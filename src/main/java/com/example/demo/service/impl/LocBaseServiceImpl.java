package com.example.demo.service.impl;

import com.example.demo.entity.LocBase;
import com.example.demo.mapper.LocBaseMapper;
import com.example.demo.service.LocBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocBaseServiceImpl implements LocBaseService {

    @Autowired
    private LocBaseMapper locBaseMapper;

    @Override
    public List<LocBase> findAllBases() {
        // 这里以后可以加逻辑，比如：只查状态为“启用”的基地
        // 或者对敏感地址进行脱敏处理
        return locBaseMapper.findAll();
    }
}