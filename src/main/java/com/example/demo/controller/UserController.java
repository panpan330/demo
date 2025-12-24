package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

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

            // ⭐ 准备返回给前端的用户信息
            Map<String, Object> userInfo = new HashMap<>();

            // ✅ 关键点 1：返回 ID (前端查档案必须要用这个)
            userInfo.put("id", user.getId());

            // ✅ 关键点 2：返回姓名 (注意 getter 是 getName)
            userInfo.put("name", user.getName());

            userInfo.put("role", user.getRole());

            // ✅ 关键点 3：返回头像 (优先用数据库里的，没有就用默认)
            String avatarUrl = user.getAvatar();
            if (avatarUrl == null || avatarUrl.isEmpty()) {
                avatarUrl = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";
            }
            userInfo.put("avatar", avatarUrl);

            result.put("userInfo", userInfo);
        } else {
            result.put("code", 401);
            result.put("msg", "用户名或密码错误");
        }
        return result;
    }
}