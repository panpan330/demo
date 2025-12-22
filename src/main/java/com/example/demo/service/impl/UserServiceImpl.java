package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        // 1. 去数据库查这个用户
        User user = userMapper.findByUsername(username);

        // 2. 如果用户不存在
        if (user == null) {
            return null;
        }

        // 3. 比对密码 (实际项目中这里应该用 BCryptPasswordEncoder 加密比对)
        // 这里为了简单，我们假设数据库存的是明文，或者你自己手动对比
        if (!user.getPassword().equals(password)) {
            return null; // 密码错
        }

        // 4. 登录成功
        return user;
    }
}