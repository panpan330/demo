package com.example.demo.service;

import com.example.demo.entity.User;
import java.util.Map;

public interface UserService {

    /**
     * 用户登录
     * @param user 前端传来的用户对象（包含 username, password）
     * @return 包含 token 和 userInfo 的 Map
     */
    Map<String, Object> login(User user);

    /**
     * 注册/新增用户
     * @param user 新用户对象
     */
    void register(User user);
}