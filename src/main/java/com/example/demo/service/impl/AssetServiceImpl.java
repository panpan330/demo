package com.example.demo.service.impl;

import com.example.demo.entity.AssetDevice;
import com.example.demo.mapper.AssetMapper;
import com.example.demo.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetMapper assetMapper;

    @Override
    public List<AssetDevice> findAll() {
        return assetMapper.findAll();
    }

    @Override
    public void addAsset(AssetDevice asset) {
        // 业务逻辑：新增时默认状态为空闲
        if (asset.getStatus() == null) {
            asset.setStatus("IDLE");
        }
        assetMapper.insert(asset);
    }

    @Override
    public void borrowAsset(Long assetId, Long userId) {
        // 1. 先查设备是否存在
        AssetDevice asset = assetMapper.findById(assetId);
        if (asset == null) {
            throw new RuntimeException("设备不存在");
        }

        // 2. 业务校验：只有空闲状态才能借
        if (!"IDLE".equals(asset.getStatus())) {
            throw new RuntimeException("手慢了！该设备已被其他人借走");
        }

        // 3. 执行借用：更新状态为 BORROWED，并写入借用人 ID
        assetMapper.updateStatusAndBorrower(assetId, "BORROWED", userId);
    }

    @Override
    public void returnAsset(Long assetId) {
        // 业务逻辑：归还时，状态变 IDLE，借用人清空 (null)
        assetMapper.updateStatusAndBorrower(assetId, "IDLE", null);
    }

    @Override
    public void deleteAsset(Long id) {
        assetMapper.deleteById(id);
    }
}