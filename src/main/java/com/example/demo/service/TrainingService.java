package com.example.demo.service;

import com.example.demo.entity.Training;
import java.util.List;

public interface TrainingService {

    // 查询所有
    List<Training> findAll();

    // 查询某人的任务
    List<Training> findByTalentId(Long talentId);

    // 新增/指派任务
    void addTraining(Training training);

    // 更新状态
    void updateStatus(Training training);

    // 删除任务
    void deleteTraining(Long id);
}