package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * 用户数据访问层
 * 对应数据库表: sys_user
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     * 用于登录校验和注册时的重复性检查
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    User selectByUsername(String username);

    /**
     * 新增用户
     * @Options: 配置自动获取数据库生成的自增 ID (id) 并回填到 user 对象中
     */
    @Insert("INSERT INTO sys_user(username, password, role, name, avatar) " +
            "VALUES(#{username}, #{password}, #{role}, #{name}, #{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

}