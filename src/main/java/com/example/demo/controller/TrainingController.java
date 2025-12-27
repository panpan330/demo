package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Training;
import com.example.demo.service.TrainingService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/training")
@CrossOrigin
public class TrainingController {

    @Resource
    private TrainingService trainingService;

    // 1. 查询列表
    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(trainingService.findAll());
    }

    // 2. 根据 TalentID 查询
    @GetMapping("/list/{talentId}")
    public Result<?> listByTalentId(@PathVariable Long talentId) {
        return Result.success(trainingService.findByTalentId(talentId));
    }

    // 3. 新增
    @PostMapping("/add")
    public Result<?> add(@RequestBody Training training) {
        try {
            trainingService.addTraining(training);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "添加失败");
        }
    }

    // 4. 更新状态
    @PostMapping("/update-status")
    public Result<?> updateStatus(@RequestBody Training training) {
        try {
            trainingService.updateStatus(training);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "更新失败");
        }
    }

    // 5. 删除
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        try {
            trainingService.deleteTraining(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "删除失败");
        }
    }
}