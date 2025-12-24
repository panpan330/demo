package com.example.demo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long userId;     // 注意：数据库里是 user_id
    private String username;
    private String password;
    private String role;
    private String fullName; // 数据库里是 full_name
    private LocalDateTime createTime;
}