package com.example.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.example.demo.entity.AssetDevice;
import com.example.demo.entity.Talent;
import com.example.demo.mapper.AssetMapper;
import com.example.demo.mapper.TalentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 👇 关键修改：如果是 Spring Boot 3.x，必须用 jakarta
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
@CrossOrigin(origins = "*")
public class ExcelController {

    @Autowired
    private TalentMapper talentMapper;

    @Autowired
    private AssetMapper assetMapper;

    // 1. 导出人才列表
    @GetMapping("/export/talent")
    public void exportTalent(HttpServletResponse response) throws IOException {
        // 设置响应头，告诉浏览器下载的是 Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("人才数据库_Backup", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 查询数据
        List<Talent> list = talentMapper.findAll();

        // 写出 Excel
        EasyExcel.write(response.getOutputStream(), Talent.class)
                .sheet("人才名单")
                .doWrite(list);
    }

    // 2. 导出资产列表
    @GetMapping("/export/asset")
    public void exportAsset(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("康复设备台账", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        List<AssetDevice> list = assetMapper.findAll();

        EasyExcel.write(response.getOutputStream(), AssetDevice.class)
                .sheet("设备清单")
                .doWrite(list);
    }
}