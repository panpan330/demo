package com.example.demo.service.impl;

import com.example.demo.entity.Training;
import com.example.demo.mapper.TrainingMapper;
import com.example.demo.service.TrainingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Resource
    private TrainingMapper trainingMapper;

    @Override
    public List<Training> findAll() {
        return trainingMapper.findAll();
    }

    @Override
    public List<Training> findByTalentId(Long talentId) {
        return trainingMapper.findByTalentId(talentId);
    }

    @Override
    public void addTraining(Training training) {
        // 业务逻辑：如果没传状态，默认 TO_DO
        if (training.getStatus() == null) {
            training.setStatus("TO_DO");
        }
        // ⭐ 修正：调用 insert
        trainingMapper.insert(training);
    }

    @Override
    public void updateStatus(Training training) {
        // ⭐ 修正：调用 update
        trainingMapper.update(training);
    }

    @Override
    public void deleteTraining(Long id) {
        // ⭐ 修正：调用 deleteById
        trainingMapper.deleteById(id);
    }
}