package com.example.demo.service;

import com.example.demo.entity.User;
import java.util.Map;

public interface UserService {

    /**
     * 用户登录，返回 Token 和 UserInfo
     */
    Map<String, Object> login(User user);

    /**
     * 注册用户
     */
    void register(User user);
}