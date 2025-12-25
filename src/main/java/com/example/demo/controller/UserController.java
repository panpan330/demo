package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin // ⭐⭐⭐ 加上这一行！允许前端跨域访问 ⭐⭐⭐
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        try {
            Map<String, Object> data = userService.login(user);
            return Result.success(data);
        } catch (RuntimeException e) {
            return Result.error("-1", e.getMessage());
        }
    }

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