package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Training;
import com.example.demo.service.TrainingService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training")
@CrossOrigin // 允许跨域
public class TrainingController {

    @Resource
    private TrainingService trainingService;

    // 获取所有
    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(trainingService.getAllTrainings());
    }

    // 获取某人
    @GetMapping("/list/{talentId}")
    public Result<?> listOne(@PathVariable Long talentId) {
        return Result.success(trainingService.getTalentTrainings(talentId));
    }

    // ⭐ AI 自动指派接口
    @PostMapping("/auto-assign/{talentId}")
    public Result<?> autoAssign(@PathVariable Long talentId) {
        try {
            List<String> courses = trainingService.autoAssign(talentId);
            return Result.success(courses);
        } catch (Exception e) {
            return Result.error("-1", e.getMessage());
        }
    }

    // 手动新增
    @PostMapping("/add")
    public Result<?> add(@RequestBody Training training) {
        trainingService.addTraining(training);
        return Result.success();
    }

    // 更新信息
    @PutMapping("/update")
    public Result<?> update(@RequestBody Training training) {
        trainingService.updateTraining(training);
        return Result.success();
    }

    // 快捷更新状态
    @PostMapping("/update-status")
    public Result<?> updateStatus(@RequestBody Training training) {
        trainingService.updateStatus(training.getId(), training.getStatus());
        return Result.success();
    }

    // 删除
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return Result.success();
    }
}