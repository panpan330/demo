package com.example.demo.service;
import com.example.demo.entity.AssetDevice;
import java.util.List;

public interface AssetService {
    List<AssetDevice> getAllDevices();
    void borrowDevice(Long deviceId);
    void returnDevice(Long deviceId);

    // ⭐ 新增 CRUD 接口定义
    void addDevice(AssetDevice device);
    void updateDevice(AssetDevice device);
    void deleteDevice(Long id);
}