package com.example.demo.service.impl;

import com.example.demo.entity.Talent;
import com.example.demo.mapper.TalentMapper;
import com.example.demo.service.TalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalentServiceImpl implements TalentService {

    @Autowired
    private TalentMapper talentMapper;

    @Override
    public List<Talent> findAll() {
        return talentMapper.findAll();
    }

    // ⭐ 实现新增逻辑
    @Override
    public void addTalent(Talent talent) {
        // 这里以后可以加逻辑，比如：判断姓名不能为空，或者分数不能小于0
        if (talent.getCsScore() == null) talent.setCsScore(0);
        if (talent.getMedScore() == null) talent.setMedScore(0);

        talentMapper.add(talent);
    }

    // ⭐ 实现删除逻辑
    @Override
    public void deleteTalent(Long id) {
        talentMapper.deleteById(id);
    }
    @Override
    public Talent getTalentByUserId(Long userId) {
        // 这里调用的 selectByUserId 必须在 Mapper 里写好了 (上一条回复里的代码)
        return talentMapper.selectByUserId(userId);
    }
}