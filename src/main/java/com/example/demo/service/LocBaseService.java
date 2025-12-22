package com.example.demo.service;

import com.example.demo.entity.LocBase;
import java.util.List;

public interface LocBaseService {
    /**
     * 获取所有基地信息
     * @return 基地列表
     */
    List<LocBase> findAllBases();
}