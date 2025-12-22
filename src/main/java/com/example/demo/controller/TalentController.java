package com.example.demo.controller;

import com.example.demo.common.Result; // 假设你封装了 Result
import com.example.demo.entity.Talent;
import com.example.demo.service.TalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/talent")
@CrossOrigin(origins = "*")
public class TalentController {

    @Autowired
    private TalentService talentService; // ⭐ 改为注入 Service

    // 查所有列表
    @GetMapping("/list")
    public List<Talent> list() {
        return talentService.findAll();
    }

    // 新增
    @PostMapping("/add")
    public Result add(@RequestBody Talent talent) {
        talentService.addTalent(talent);
        return Result.success();
    }

    // 删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        talentService.deleteTalent(id);
        return Result.success();
    }

    // ⭐ 新增接口：个人中心专用
    // 前端请求：/api/talent/me/3
    @GetMapping("/me/{userId}")
    public Result getMyInfo(@PathVariable Long userId) {
        // 调用 Service
        Talent talent = talentService.getTalentByUserId(userId);

        // 判空处理：如果这个账号只是普通用户，没有关联人才档案
        if (talent == null) {
            return Result.error("未找到关联的档案信息");
        }
        return Result.success(talent);
    }
}