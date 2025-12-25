package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Talent;
import com.example.demo.service.TalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talent")
@CrossOrigin // ⭐⭐⭐ 加上这一行！允许前端跨域访问 ⭐⭐⭐
public class TalentController {

    @Autowired
    private TalentService talentService;

    @GetMapping("/list")
    public Result<?> findAll() {
        return Result.success(talentService.findAll());
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody Talent talent) {
        try {
            talentService.addTalent(talent);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "录入失败：" + e.getMessage());
        }
    }

    // ⭐ 刚才新增的更新接口
    @PutMapping("/update")
    public Result<?> update(@RequestBody Talent talent) {
        try {
            talentService.updateTalent(talent);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "更新失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        talentService.deleteTalent(id);
        return Result.success();
    }
}