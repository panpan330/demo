package com.example.demo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Training {
    private Long id;
    private Long talentId;
    private String courseName;
    private String status;
    private LocalDateTime createTime;

    // 👇 新增：用来存人才的名字 (数据库表里没有这个字段，是查出来拼上的)
    private String talentName;
}