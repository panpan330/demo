package com.example.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.example.demo.common.Result;
import com.example.demo.entity.AssetDevice;
import com.example.demo.entity.Talent;
import com.example.demo.mapper.AssetMapper;
import com.example.demo.mapper.TalentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
@CrossOrigin(origins = "*") // 允许跨域
public class ExcelController {

    @Autowired
    private TalentMapper talentMapper;

    @Autowired
    private AssetMapper assetMapper;

    // 1. 导出人才列表 (保持不变)
    @GetMapping("/export/talent")
    public void exportTalent(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("人才数据库_Backup", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List<Talent> list = talentMapper.findAll();
        EasyExcel.write(response.getOutputStream(), Talent.class).sheet("人才名单").doWrite(list);
    }

    // 2. 导出资产列表 (保持不变)
    @GetMapping("/export/asset")
    public void exportAsset(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("康复设备台账", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List<AssetDevice> list = assetMapper.findAll();
        EasyExcel.write(response.getOutputStream(), AssetDevice.class).sheet("设备清单").doWrite(list);
    }

    // ⭐⭐ 3. 新增：Excel 批量导入人才 ⭐⭐
    @PostMapping("/import/talent")
    public Result<?> importTalent(@RequestParam("file") MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), Talent.class, new ReadListener<Talent>() {
                /**
                 * 每解析一行 Excel，就会调用一次 invoke
                 */
                @Override
                public void invoke(Talent talent, AnalysisContext context) {
                    // 安全处理：强制 ID 为空，让数据库自动生成自增 ID
                    talent.setId(null);

                    // 安全处理：给必填项默认值，防止报错
                    if(talent.getRole() == null) talent.setRole("STUDENT");
                    if(talent.getCsScore() == null) talent.setCsScore(60);
                    if(talent.getMedScore() == null) talent.setMedScore(60);

                    // 存入数据库
                    try {
                        talentMapper.add(talent);
                    } catch (Exception e) {
                        // 某一行报错不要紧，打印日志继续下一行
                        System.err.println("导入失败: " + talent.getName());
                    }
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    System.out.println("Excel 解析完成！");
                }
            }).sheet().doRead();

            return Result.success("导入成功！");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("-1", "文件解析失败");
        }
    }
}