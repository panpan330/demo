package com.example.demo.entity;

import lombok.Data;
import java.util.Map;

@Data
public class StatisticsVO {
    private int totalTalent;       // 总人数
    private int trainingCount;     // 进行中的培训
    private double avgCsScore;     // 平均计算机分
    private double avgMedScore;    // 平均医学分
    // 用于饼图的数据：{ "技术型": 5, "临床型": 3 ... }
    private Map<String, Integer> typeDistribution;
}