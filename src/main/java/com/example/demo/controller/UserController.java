package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        // 简单的判空
        if (user.getUsername() == null || user.getPassword() == null) {
            return Result.error("-1", "账号或密码不能为空");
        }

        try {
            // ⭐ 直接调用 Service 的 login 方法，它会返回包含 token 和 user 的 Map
            Map<String, Object> map = userService.login(user);
            return Result.success(map);
        } catch (Exception e) {
            return Result.error("-1", e.getMessage());
        }
    }

    /**
     * 注册接口
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return Result.error("-1", "输入不合法");
        }

        try {
            // ⭐ 直接调用 Service 的 register 方法
            userService.register(user);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", e.getMessage());
        }
    }
}