package com.example.demo.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.example.demo.common.JwtUtils;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource; // Spring Boot 3.x 使用 jakarta，旧版本用 javax

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> login(User user) {
        // 1. 查询用户
        // 注意：需确保 UserMapper 中有 selectByUsername 方法
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 校验密码 (兼容逻辑)
        boolean isMatch = false;
        // 情况A: 明文直接匹配（兼容旧账号）
        if (dbUser.getPassword().equals(user.getPassword())) {
            isMatch = true;
        } else {
            // 情况B: 尝试 BCrypt 加密匹配
            try {
                isMatch = BCrypt.checkpw(user.getPassword(), dbUser.getPassword());
            } catch (Exception e) {
                isMatch = false;
            }
        }

        if (!isMatch) {
            throw new RuntimeException("密码错误");
        }

        // 3. 生成 Token
        String token = JwtUtils.createToken(dbUser.getId(), dbUser.getRole());

        // 4. 封装返回结果
        dbUser.setPassword(null); // 擦除密码，防止泄露

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("userInfo", dbUser);

        return resultMap;
    }

    @Override
    public void register(User user) {
        // 1. 校验用户名是否已存在
        User exist = userMapper.selectByUsername(user.getUsername());
        if (exist != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 2. 设置默认密码
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            user.setPassword("123456");
        }

        // 3. 密码加密 (使用 Hutool BCrypt)
        String rawPwd = user.getPassword();
        String encodedPwd = BCrypt.hashpw(rawPwd);
        user.setPassword(encodedPwd);

        // 4. 插入数据库
        userMapper.insert(user);
    }
}