package com.example.demo.controller;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 👇 1. 内部直接定义一个简单 Mapper，省去建文件
@Mapper
interface ConfigMapper {
    @Select("SELECT * FROM sys_config")
    List<Map<String, String>> findAll();

    @Update("UPDATE sys_config SET config_value = #{val} WHERE config_key = #{key}")
    void update(String key, String val);
}

// 👇 2. 控制器
@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins = "*")
public class ConfigController {

    @Autowired
    private ConfigMapper configMapper;

    // 获取所有配置，转成 Map 给前端：{ "cs_threshold": "70", ... }
    @GetMapping("/all")
    public Map<String, Integer> getAll() {
        List<Map<String, String>> list = configMapper.findAll();
        Map<String, Integer> result = new HashMap<>();
        for (Map<String, String> item : list) {
            // 把数据库的 String 转成 Integer 方便前端计算
            result.put(item.get("config_key"), Integer.parseInt(item.get("config_value")));
        }
        return result;
    }

    // 更新配置
    @PostMapping("/update")
    public String update(@RequestBody Map<String, Integer> params) {
        // 前端传过来 { "cs_threshold": 80, "med_threshold": 60 }
        params.forEach((key, val) -> {
            configMapper.update(key, String.valueOf(val));
        });
        return "success";
    }
}