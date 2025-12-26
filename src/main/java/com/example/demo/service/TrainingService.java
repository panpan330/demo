package com.example.demo.service;

import com.example.demo.entity.Training;
import java.util.List;

public interface TrainingService {
    List<Training> getAllTrainings();
    List<Training> getTalentTrainings(Long talentId);

    // ⭐ AI 核心功能
    List<String> autoAssign(Long talentId);

    void addTraining(Training training);
    void assignTraining(Training training); // 用于去重插入
    void updateTraining(Training training);
    void updateStatus(Long id, String status);
    void deleteTraining(Long id);
}