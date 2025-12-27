package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Training;
import com.example.demo.service.TrainingService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List; // 记得引入 List

@RestController
@RequestMapping("/api/training")
@CrossOrigin // 允许跨域
public class TrainingController {

    @Resource
    private TrainingService trainingService;

    // 1. 查询所有列表
    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(trainingService.findAll());
    }

    // 2. 根据 TalentID 查询个人的培训记录
    @GetMapping("/list/{talentId}")
    public Result<?> listByTalentId(@PathVariable Long talentId) {
        return Result.success(trainingService.findByTalentId(talentId));
    }

    // 3. 新增培训任务
    @PostMapping("/add")
    public Result<?> add(@RequestBody Training training) {
        try {
            trainingService.addTraining(training);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "添加失败");
        }
    }

    // 4. 更新状态 (TO_DO -> IN_PROGRESS -> DONE)
    @PostMapping("/update-status")
    public Result<?> updateStatus(@RequestBody Training training) {
        try {
            trainingService.updateStatus(training);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "更新失败");
        }
    }

    // 5. 删除培训任务
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        try {
            trainingService.deleteTraining(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "删除失败");
        }
    }

    // ⭐⭐ 6. 补全：AI 自动指派任务接口 ⭐⭐
    @PostMapping("/auto-assign/{talentId}")
    public Result<?> autoAssign(@PathVariable Long talentId) {
        try {
            // 调用 Service 层的 autoAssign 方法，并返回指派的课程列表
            List<Training> assignedList = trainingService.autoAssign(talentId);
            return Result.success(assignedList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "指派失败: " + e.getMessage());
        }
    }
}