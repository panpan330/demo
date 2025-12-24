package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService; // 👈 引入 Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService; // 👈 注入大厨

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Map<String, Object> result = new HashMap<>();

        // 调用 Service 进行数据库校验
        User user = userService.login(username, password);

        if (user != null) {
            result.put("code", 200);
            result.put("msg", "登录成功");
            result.put("token", "token-" + user.getUsername() + "-" + System.currentTimeMillis());

            // 返回数据库里的真实姓名
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("name", user.getFullName()); // 例如 "系统管理员"
            userInfo.put("role", user.getRole());     // 例如 "ADMIN"
            userInfo.put("avatar", "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");

            result.put("userInfo", userInfo);
        } else {
            result.put("code", 401);
            result.put("msg", "用户名或密码错误");
        }
        return result;
    }
}