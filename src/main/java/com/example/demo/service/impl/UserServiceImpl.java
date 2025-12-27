package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Map<String, Object> login(User user) {
        User dbUser = userMapper.findByUsername(user.getUsername());
        if (dbUser == null) {
            throw new RuntimeException("用户不存在");
        }

        boolean isMatch = false;
        String inputPass = user.getPassword();
        String dbPass = dbUser.getPassword();

        if (dbPass.equals(inputPass)) {
            isMatch = true;
        } else if (passwordEncoder.matches(inputPass, dbPass)) {
            isMatch = true;
        }

        if (!isMatch) {
            throw new RuntimeException("用户名或密码错误");
        }

        String token = "TOKEN-" + UUID.randomUUID().toString();
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", dbUser);
        return result;
    }

    @Override
    public void register(User user) {
        User exist = userMapper.findByUsername(user.getUsername());
        if (exist != null) {
            throw new RuntimeException("该账号已存在");
        }
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
    }

    @Override
    public void changePassword(Long userId, String oldPass, String newPass) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        String dbPass = user.getPassword();
        boolean isMatch = false;
        if (dbPass.equals(oldPass)) isMatch = true;
        else if (passwordEncoder.matches(oldPass, dbPass)) isMatch = true;

        if (!isMatch) throw new RuntimeException("旧密码错误");

        userMapper.updatePassword(userId, passwordEncoder.encode(newPass));
    }

    @Override
    public void resetPassword(Long userId, String newPassword) {
        User user = userMapper.findById(userId);
        if (user == null) throw new RuntimeException("用户不存在");
        userMapper.updatePassword(userId, passwordEncoder.encode(newPassword));
    }

    /**
     * ⭐ 新增：根据用户名重置密码的实现
     */
    @Override
    public void resetPasswordByUsername(String username, String newPass) {
        // 1. 查是否存在
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("该账号未注册，无法重置");
        }
        // 2. 加密并更新
        String encryptedPass = passwordEncoder.encode(newPass);
        userMapper.updatePasswordByUsername(username, encryptedPass);
    }
}