package com.example.demo.service.impl;

import com.example.demo.entity.Talent;
import com.example.demo.entity.Training;
import com.example.demo.mapper.TalentMapper;
import com.example.demo.mapper.TrainingMapper;
import com.example.demo.service.TrainingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // ⭐ 改用这个包
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Resource
    private TrainingMapper trainingMapper;

    @Resource
    private TalentMapper talentMapper;

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
        if (training.getStatus() == null) {
            training.setStatus("TO_DO");
        }
        // 如果没传时间，自动填入当前时间
        if (training.getCreateTime() == null) {
            training.setCreateTime(LocalDateTime.now());
        }
        trainingMapper.insert(training);
    }

    @Override
    public void updateStatus(Training training) {
        trainingMapper.update(training);
    }

    @Override
    public void deleteTraining(Long id) {
        trainingMapper.deleteById(id);
    }

    // ⭐⭐ AI 自动指派逻辑 ⭐⭐
    @Override
    public List<Training> autoAssign(Long talentId) {
        // 1. 查人才信息
        Talent talent = talentMapper.findById(talentId);
        if (talent == null) {
            throw new RuntimeException("人才不存在");
        }

        List<Training> newTrainings = new ArrayList<>();

        // 2. 策略逻辑
        if (talent.getCsScore() != null && talent.getCsScore() < 60) {
            createIfNotExists(talentId, "计算机导论", newTrainings);
        }
        if (talent.getMedScore() != null && talent.getMedScore() < 60) {
            createIfNotExists(talentId, "基础康复学", newTrainings);
        }
        if (talent.getCsScore() != null && talent.getMedScore() != null
                && talent.getCsScore() > 80 && talent.getMedScore() > 80) {
            createIfNotExists(talentId, "科研项目管理", newTrainings);
        }

        return newTrainings;
    }

    // 辅助方法
    private void createIfNotExists(Long talentId, String courseName, List<Training> list) {
        Training t = new Training();
        t.setTalentId(talentId);
        t.setCourseName(courseName);
        t.setStatus("TO_DO");

        // ⭐⭐ 核心修复：使用 LocalDateTime.now() 代替 new Date() ⭐⭐
        t.setCreateTime(LocalDateTime.now());

        trainingMapper.insert(t);
        list.add(t);
    }
}