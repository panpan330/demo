package com.example.demo.service.impl;

import com.example.demo.entity.Talent;
import com.example.demo.entity.Training;
import com.example.demo.mapper.TalentMapper;
import com.example.demo.mapper.TrainingMapper;
import com.example.demo.service.TrainingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Resource
    private TrainingMapper trainingMapper;

    @Resource
    private TalentMapper talentMapper; // ⭐ 需要查人才分数，必须注入这个

    @Override
    public List<Training> getAllTrainings() {
        return trainingMapper.findAll();
    }

    @Override
    public List<Training> getTalentTrainings(Long talentId) {
        return trainingMapper.findByTalentId(talentId);
    }

    // ⭐⭐ AI 智能推荐核心算法 ⭐⭐
    @Override
    @Transactional
    public List<String> autoAssign(Long talentId) {
        // 1. 查人才详情
        List<Talent> all = talentMapper.findAll();
        Talent talent = all.stream().filter(t -> t.getId().equals(talentId)).findFirst().orElse(null);

        if (talent == null) {
            throw new RuntimeException("人才不存在");
        }

        List<String> newCourses = new ArrayList<>();
        int cs = talent.getCsScore();
        int med = talent.getMedScore();

        // --- 🧠 算法规则 ---
        if (cs < 60) {
            newCourses.add("Python 编程基础");
            newCourses.add("计算机导论");
        }
        if (med < 60) {
            newCourses.add("系统解剖学");
            newCourses.add("康复评定技术");
        }
        if (cs > 80 && med < 60) {
            newCourses.add("医学统计学"); // 补短板
        }
        if (cs > 80 && med > 80) {
            newCourses.add("高级科研项目管理");
            newCourses.add("Nature 论文写作指导");
        }
        // 保底
        if (newCourses.isEmpty()) {
            newCourses.add("医工交叉前沿讲座");
        }

        // --- 批量入库 (使用 assign 方法去重) ---
        for (String course : newCourses) {
            Training task = new Training();
            task.setTalentId(talentId);
            task.setCourseName(course);
            trainingMapper.assign(task);
        }
        return newCourses;
    }

    @Override
    public void addTraining(Training training) {
        trainingMapper.add(training);
    }

    @Override
    public void assignTraining(Training training) {
        trainingMapper.assign(training);
    }

    @Override
    public void updateTraining(Training training) {
        trainingMapper.update(training);
    }

    @Override
    public void updateStatus(Long id, String status) {
        trainingMapper.updateStatus(id, status);
    }

    @Override
    public void deleteTraining(Long id) {
        trainingMapper.delete(id);
    }
}