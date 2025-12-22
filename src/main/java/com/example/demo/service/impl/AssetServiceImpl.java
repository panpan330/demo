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
    public List<AssetDevice> getAllDevices() { return assetMapper.findAll(); }

    @Override
    public void borrowDevice(Long deviceId) { assetMapper.borrowDevice(deviceId); }

    @Override
    public void returnDevice(Long deviceId) { assetMapper.returnDevice(deviceId); }

    // ⭐ 实现 CRUD
    @Override
    public void addDevice(AssetDevice device) { assetMapper.add(device); }

    @Override
    public void updateDevice(AssetDevice device) { assetMapper.update(device); }

    @Override
    public void deleteDevice(Long id) { assetMapper.delete(id); }
}