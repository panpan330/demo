package com.example.demo.controller;

import com.example.demo.entity.Training;
import com.example.demo.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/training")
@CrossOrigin(origins = "*")
public class TrainingController {

    @Autowired private TrainingService trainingService;

    // ⭐ 修改点：返回值改为 List<Training>
    @GetMapping("/all")
    public List<Training> all() {
        return trainingService.getAllTrainings(); // 👈 这里改了
    }

    // ... 其他方法保持不变 ...
    @GetMapping("/list/{talentId}")
    public List<Training> listOne(@PathVariable Long talentId) { return trainingService.getTalentTrainings(talentId); }

    @PostMapping("/add")
    public String add(@RequestBody Training training) {
        trainingService.addTraining(training);
        return "success";
    }

    @PostMapping("/assign")
    public String assignBatch(@RequestBody List<Training> list) {
        for (Training t : list) {
            trainingService.addTraining(t);
        }
        return "success";
    }

    @PutMapping("/update")
    public String update(@RequestBody Training training) {
        trainingService.updateTraining(training);
        return "success";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return "success";
    }
}