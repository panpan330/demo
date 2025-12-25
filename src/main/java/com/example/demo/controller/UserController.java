package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        try {
            // 逻辑全在 Service 里，这里只负责接收结果
            Map<String, Object> data = userService.login(user);
            return Result.success(data);
        } catch (RuntimeException e) {
            // 捕获 Service 抛出的异常（如“密码错误”）并封装返回
            return Result.error("-1", e.getMessage());
        }
    }

    /**
     * 新增用户接口
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody User user) {
        try {
            userService.register(user);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.error("-1", e.getMessage());
        }
    }
}