package com.example.demo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    // ⭐ 必须叫 id (对应数据库主键 id)
    private Long id;

    private String username;
    private String password;
    private String role;

    // ⭐ 必须叫 name (对应数据库列名 name)
    private String name;

    // ⭐ 必须有 avatar (对应数据库列名 avatar)
    private String avatar;

    private LocalDateTime createTime;
}