package com.example.demo.service.impl;

import com.example.demo.entity.Training;
import com.example.demo.mapper.TrainingMapper;
import com.example.demo.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired private TrainingMapper trainingMapper;

    @Override
    public List<Training> getAllTrainings() {
        return trainingMapper.findAll(); // 👈 这里改了
    }

    @Override
    public List<Training> getTalentTrainings(Long talentId) { return trainingMapper.findByTalentId(talentId); }

    @Override
    public void addTraining(Training training) { trainingMapper.add(training); }

    @Override
    public void updateTraining(Training training) { trainingMapper.update(training); }

    @Override
    public void deleteTraining(Long id) { trainingMapper.delete(id); }
}