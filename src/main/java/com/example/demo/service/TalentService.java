package com.example.demo.service;

import com.example.demo.entity.Talent;
import java.util.List;

public interface TalentService {
    List<Talent> findAll();

    // ⭐ 新增：添加人才
    void addTalent(Talent talent);

    // ⭐ 新增：删除人才
    void deleteTalent(Long id);

    Talent getTalentByUserId(Long userId);
    // ... 其他方法
    void updateTalent(Talent talent); // 新增这一行
}