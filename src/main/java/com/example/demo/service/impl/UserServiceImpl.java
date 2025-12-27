package com.example.demo.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.example.demo.common.JwtUtils; // 确保你有这个工具类
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> login(User user) {
        // 1. 查询用户
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 校验密码
        boolean isMatch = false;
        // 兼容逻辑：先试明文，再试加密
        if (dbUser.getPassword().equals(user.getPassword())) {
            isMatch = true;
        } else {
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
        dbUser.setPassword(null); // 擦除密码

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        // ⭐⭐ 关键修改：Key 必须叫 "user"，因为前端 Login.vue 读的是 res.data.user
        resultMap.put("user", dbUser);

        return resultMap;
    }

    @Override
    public void register(User user) {
        // 1. 校验存在
        User exist = userMapper.selectByUsername(user.getUsername());
        if (exist != null) {
            throw new RuntimeException("用户名已存在");
        }
        // 2. 默认密码
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            user.setPassword("123456");
        }
        // 3. 加密
        user.setPassword(BCrypt.hashpw(user.getPassword()));

        // 4. 默认角色
        if (user.getRole() == null) {
            user.setRole("STUDENT");
        }

        // 5. 插入
        userMapper.insert(user);
    }

    // 如果你还需要 selectByUsername 给其他地方用，可以在这里加上，并在接口里声明
    // 但按照标准写法，Controller 不应该直接调这个
}