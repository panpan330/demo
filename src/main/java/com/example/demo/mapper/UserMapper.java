package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    // 1. 登录/查重用
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    User findByUsername(String username);

    // 2. ⭐ 新增用户 (关键修复)
    // useGeneratedKeys=true: 告诉数据库"你生成了ID记得告诉我"
    // keyProperty="id": 告诉MyBatis"拿回来的ID填到User对象的id属性里"
    @Insert("INSERT INTO sys_user(username, password, role, name, avatar) " +
            "VALUES(#{username}, #{password}, #{role}, #{name}, #{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);
}