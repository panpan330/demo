package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.AssetDevice;
import com.example.demo.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/asset")
@CrossOrigin
public class AssetController {

    @Autowired
    private AssetService assetService;

    // 1. 列表查询
    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(assetService.findAll());
    }

    // 2. 新增
    @PostMapping("/add")
    public Result<?> add(@RequestBody AssetDevice asset) {
        try {
            assetService.addAsset(asset);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "入库失败：" + e.getMessage());
        }
    }

    // 3. 借用设备
    @PostMapping("/borrow")
    public Result<?> borrow(@RequestBody Map<String, Object> params) {
        // 参数校验
        if (params.get("assetId") == null || params.get("userId") == null) {
            return Result.error("-1", "参数缺失");
        }

        Long assetId = Long.valueOf(params.get("assetId").toString());
        Long userId = Long.valueOf(params.get("userId").toString());

        try {
            // ⭐ 所有的判断逻辑都在 Service 里，Controller 直接调
            assetService.borrowAsset(assetId, userId);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.error("-1", e.getMessage()); // 捕获 "设备已被借走" 等异常
        }
    }

    // 4. 归还设备
    @PostMapping("/return")
    public Result<?> returnAsset(@RequestBody Map<String, Object> params) {
        if (params.get("assetId") == null) {
            return Result.error("-1", "参数缺失");
        }
        Long assetId = Long.valueOf(params.get("assetId").toString());

        try {
            assetService.returnAsset(assetId);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "归还失败：" + e.getMessage());
        }
    }

    // 5. 删除
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        try {
            assetService.deleteAsset(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "删除失败");
        }
    }
}