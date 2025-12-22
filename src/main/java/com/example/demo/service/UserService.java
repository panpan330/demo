package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    // 登录方法：成功返回User对象，失败返回null
    User login(String username, String password);
}