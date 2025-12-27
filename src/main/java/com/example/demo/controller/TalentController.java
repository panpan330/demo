package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Talent;
import com.example.demo.service.TalentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 人才管理控制层
 * 功能包含：
 * 1. 管理员用：增删改查、仪表盘统计
 * 2. 学生/医生用：查询个人档案 (My Profile)
 */
@RestController
@RequestMapping("/api/talent")
@CrossOrigin // 允许跨域
public class TalentController {

    @Resource
    private TalentService talentService;

    /**
     * ⭐ 新增：根据 UserID 获取人才档案
     * 用于“个人中心”页面加载当前登录用户的信息
     */
    @GetMapping("/profile/{userId}")
    public Result<?> getProfile(@PathVariable Long userId) {
        // 调用 Service 层的新方法
        Talent talent = talentService.getTalentByUserId(userId);

        if (talent != null) {
            return Result.success(talent);
        } else {
            // 如果没找到（比如管理员账号没有关联人才档案），返回错误提示
            return Result.error("-1", "未找到关联的人才档案，请联系管理员录入");
        }
    }

    /**
     * 1. 查询所有人才列表 (管理员用)
     */
    @GetMapping("/list")
    public Result<?> findAll() {
        return Result.success(talentService.findAll());
    }

    /**
     * 2. 新增人才
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody Talent talent) {
        try {
            talentService.addTalent(talent);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "录入失败：" + e.getMessage());
        }
    }

    /**
     * 3. 更新人才信息
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody Talent talent) {
        try {
            talentService.updateTalent(talent);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "更新失败：" + e.getMessage());
        }
    }

    /**
     * 4. 删除人才
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        try {
            talentService.deleteTalent(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "删除失败：" + e.getMessage());
        }
    }

    /**
     * 5. 首页仪表盘统计数据接口
     * 不需要改数据库，直接查出来在内存里计算
     */
    @GetMapping("/dashboard")
    public Result<?> getDashboardData() {
        // 先查出所有人
        List<Talent> talents = talentService.findAll();

        Map<String, Object> data = new HashMap<>();

        // --- 统计 1: 核心数字 ---
        data.put("totalCount", talents.size());

        // 计算平均分 (使用 Java Stream流，防止除以0)
        double avgCs = talents.isEmpty() ? 0 : talents.stream().mapToInt(Talent::getCsScore).average().orElse(0);
        double avgMed = talents.isEmpty() ? 0 : talents.stream().mapToInt(Talent::getMedScore).average().orElse(0);

        // 保留1位小数
        data.put("avgCs", String.format("%.1f", avgCs));
        data.put("avgMed", String.format("%.1f", avgMed));

        // --- 统计 2: 角色分布 (饼图数据) ---
        // 1. 分组计数: { "STUDENT": 5, "DOCTOR": 3 ... }
        Map<String, Long> roleCount = talents.stream()
                .collect(Collectors.groupingBy(Talent::getRole, Collectors.counting()));

        // 2. 转换成 ECharts 需要的格式: [{name: '学生', value: 5}, ...]
        List<Map<String, Object>> pieData = roleCount.entrySet().stream().map(entry -> {
            Map<String, Object> item = new HashMap<>();
            String roleCode = entry.getKey();
            String roleName = roleCode; // 默认用英文

            // 简单的汉化映射
            if ("STUDENT".equals(roleCode)) roleName = "学生";
            if ("DOCTOR".equals(roleCode)) roleName = "医生";
            if ("RESEARCHER".equals(roleCode)) roleName = "研究员";
            if ("TEACHER".equals(roleCode)) roleName = "教师";

            item.put("name", roleName);
            item.put("value", entry.getValue());
            return item;
        }).collect(Collectors.toList());

        data.put("pieData", pieData);

        // --- 统计 3: 能力散点图数据 ---
        // 格式: [[CS分, 医学分, 姓名, 角色], [80, 90, "张三", "DOCTOR"], ...]
        List<Object[]> scatterData = talents.stream().map(t -> new Object[]{
                t.getCsScore(),
                t.getMedScore(),
                t.getName(),
                t.getRole()
        }).collect(Collectors.toList());

        data.put("scatterData", scatterData);

        return Result.success(data);
    }
}