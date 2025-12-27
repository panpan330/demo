package com.example.demo.service;

import com.example.demo.entity.User;
import java.util.Map;

public interface UserService {

    Map<String, Object> login(User user);

    void register(User user);

    void changePassword(Long userId, String oldPass, String newPass);

    // 旧的基于ID的重置方法 (保留也行，但主要用下面这个)
    void resetPassword(Long userId, String newPassword);

    // ⭐ 新增：基于用户名的重置方法
    void resetPasswordByUsername(String username, String newPass);
}