package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin // 允许跨域
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return Result.error("-1", "账号或密码不能为空");
        }
        try {
            Map<String, Object> map = userService.login(user);
            return Result.success(map);
        } catch (Exception e) {
            return Result.error("-1", e.getMessage());
        }
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return Result.error("-1", "输入不合法");
        }
        try {
            userService.register(user);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", e.getMessage());
        }
    }

    @PostMapping("/password")
    public Result<?> changePassword(@RequestBody Map<String, String> params) {
        String userIdStr = params.get("userId");
        String oldPass = params.get("oldPass");
        String newPass = params.get("newPass");

        if (userIdStr == null || oldPass == null || newPass == null) {
            return Result.error("-1", "参数不完整");
        }
        try {
            userService.changePassword(Long.valueOf(userIdStr), oldPass, newPass);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", e.getMessage());
        }
    }

    /**
     * ⭐ 修改：管理员重置密码 (接收 username)
     */
    @PostMapping("/reset-password")
    public Result<?> resetPassword(@RequestBody Map<String, Object> params) {
        // 获取前端传来的 username
        String username = (String) params.get("username");
        String newPass = (String) params.get("newPassword");

        if (username == null || newPass == null) {
            return Result.error("-1", "账号或新密码不能为空");
        }

        try {
            // 调用新的根据用户名重置的方法
            userService.resetPasswordByUsername(username, newPass);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", e.getMessage());
        }
    }
}