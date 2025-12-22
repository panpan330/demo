package com.example.demo.controller;

import com.example.demo.entity.AssetDevice;
import com.example.demo.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/asset")
@CrossOrigin(origins = "*")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/list")
    public List<AssetDevice> list() { return assetService.getAllDevices(); }

    @PostMapping("/borrow/{id}")
    public String borrow(@PathVariable Long id) {
        assetService.borrowDevice(id);
        return "success";
    }

    @PostMapping("/return/{id}")
    public String returnDev(@PathVariable Long id) {
        assetService.returnDevice(id);
        return "success";
    }

    // ⭐ 新增 CRUD 接口
    @PostMapping("/add")
    public String add(@RequestBody AssetDevice device) {
        assetService.addDevice(device);
        return "success";
    }

    @PutMapping("/update")
    public String update(@RequestBody AssetDevice device) {
        assetService.updateDevice(device);
        return "success";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        assetService.deleteDevice(id);
        return "success";
    }
}