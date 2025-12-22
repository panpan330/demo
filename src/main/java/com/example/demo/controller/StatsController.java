package com.example.demo.controller;

import com.example.demo.entity.StatisticsVO;
import com.example.demo.entity.Talent;
import com.example.demo.entity.Training;
import com.example.demo.mapper.TalentMapper;
import com.example.demo.mapper.TrainingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "*")
public class StatsController {

    @Autowired
    private TalentMapper talentMapper;

    @Autowired
    private TrainingMapper trainingMapper; // 如果没有这个mapper，请确保之前创建了

    @GetMapping("/summary")
    public StatisticsVO getSummary() {
        StatisticsVO vo = new StatisticsVO();
        List<Talent> talents = talentMapper.findAll();

        // 1. 基础统计
        vo.setTotalTalent(talents.size());

        // 2. 计算平均分
        double totalCs = 0;
        double totalMed = 0;
        Map<String, Integer> typeMap = new HashMap<>();
        typeMap.put("技术驱动型", 0);
        typeMap.put("临床应用型", 0);
        typeMap.put("卓越交叉型", 0);
        typeMap.put("成长期待型", 0);

        for (Talent t : talents) {
            totalCs += t.getCsScore();
            totalMed += t.getMedScore();

            // 3. 统计人才类型 (逻辑要和前端保持一致)
            String type = getType(t.getCsScore(), t.getMedScore());
            typeMap.put(type, typeMap.get(type) + 1);
        }

        if (talents.size() > 0) {
            vo.setAvgCsScore(Math.round(totalCs / talents.size()));
            vo.setAvgMedScore(Math.round(totalMed / talents.size()));
        }

        vo.setTypeDistribution(typeMap);

        // 4. 获取正在进行的培训数量 (这里简单模拟，实际需查数据库)
        // 如果你之前的 TrainingMapper 有 findAll 方法可以用，这里为了防报错先写死或查某个人
        vo.setTrainingCount(5); // 暂时写个假数字，或者你可以扩展 SQL 去查 count(*)

        return vo;
    }

    // 辅助方法：判断类型
    private String getType(int cs, int med) {
        if (cs > 70 && med > 70) return "卓越交叉型";
        if (cs > med + 20) return "技术驱动型";
        if (med > cs + 20) return "临床应用型";
        return "成长期待型";
    }
}