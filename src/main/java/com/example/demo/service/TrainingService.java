package com.example.demo.service;
import com.example.demo.entity.Training;
import java.util.List;
import java.util.Map;

public interface TrainingService {
    List<Training> getAllTrainings();
    List<Training> getTalentTrainings(Long talentId);
    void addTraining(Training training);
    void updateTraining(Training training);
    void deleteTraining(Long id);
}