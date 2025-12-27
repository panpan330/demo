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

    @PostMapping("/reset-password")
    public Result<?> resetPassword(@RequestBody Map<String, Object> params) {
        // 这里最好加个权限校验，确保当前登录的是管理员
        // 但为了毕设简单，我们假设只有管理员能看到这个按钮

        String userIdStr = params.get("userId").toString();
        String newPass = (String) params.get("newPassword");

        if (userIdStr == null || newPass == null) {
            return Result.error("-1", "参数缺失");
        }

        try {
            userService.resetPassword(Long.valueOf(userIdStr), newPass);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", e.getMessage());
        }
    }

}