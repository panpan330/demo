package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 需要 spring-boot-starter-security 依赖
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    // 密码加密工具 (Spring Security 自带)
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 登录业务
     */
    @Override
    public Map<String, Object> login(User user) {
        // 1. 根据用户名查询用户
        User dbUser = userMapper.findByUsername(user.getUsername());
        if (dbUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 核心：双重密码校验逻辑 (兼容老数据)
        boolean isMatch = false;
        String inputPass = user.getPassword(); // 用户输入的
        String dbPass = dbUser.getPassword();  // 数据库存的

        // 情况 A：数据库存的是明文 (比如 admin: 123456)
        if (dbPass.equals(inputPass)) {
            isMatch = true;
        }
        // 情况 B：数据库存的是加密密文 (比如 $2a$10$...)
        else if (passwordEncoder.matches(inputPass, dbPass)) {
            isMatch = true;
        }

        if (!isMatch) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 3. 生成 Token
        // 如果你有专门的 JwtUtils，请在这里替换，例如: String token = JwtUtils.createToken(dbUser.getId());
        // 这里用 UUID 做演示，保证代码不报错
        String token = "TOKEN-" + UUID.randomUUID().toString();

        // 4. 组装返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", dbUser); // 返回用户信息给前端存储
        return result;
    }

    /**
     * 注册业务
     */
    @Override
    public void register(User user) {
        // 1. 查重
        User exist = userMapper.findByUsername(user.getUsername());
        if (exist != null) {
            throw new RuntimeException("该账号已存在");
        }

        // 2. 设置默认密码
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }

        // 3. 为了安全，建议加密存储 (这样入库的就是 $2a$10$...)
        // 如果你想存明文，就把下面这行注释掉
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userMapper.insert(user);
    }

    /**
     * 修改密码业务
     */
    @Override
    public void changePassword(Long userId, String oldPass, String newPass) {
        // 1. 查出用户
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 校验旧密码 (同样使用双重校验，防止报错)
        String dbPass = user.getPassword();
        boolean isMatch = false;

        if (dbPass.equals(oldPass)) {
            isMatch = true; // 明文匹配
        } else if (passwordEncoder.matches(oldPass, dbPass)) {
            isMatch = true; // 加密匹配
        }

        if (!isMatch) {
            throw new RuntimeException("旧密码错误");
        }

        // 3. 加密新密码并更新
        String encryptedNewPass = passwordEncoder.encode(newPass);
        userMapper.updatePassword(userId, encryptedNewPass);
    }
    @Override
    public void resetPassword(Long userId, String newPassword) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 直接加密新密码并覆盖 (如果你之前降级为不加密，这里直接传 newPassword 即可)
        String encryptedPass = passwordEncoder.encode(newPassword);

        userMapper.updatePassword(userId, encryptedPass);
    }
}