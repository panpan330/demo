package com.example.demo.service;

import com.example.demo.entity.AssetDevice;
import java.util.List;

public interface AssetService {
    // 查询所有
    List<AssetDevice> findAll();

    // 新增设备
    void addAsset(AssetDevice asset);

    // 借用设备 (核心业务)
    void borrowAsset(Long assetId, Long userId);

    // 归还设备 (核心业务)
    void returnAsset(Long assetId);

    // 删除设备
    void deleteAsset(Long id);
}