package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    User selectByUsername(String username);

    @Insert("INSERT INTO sys_user(username, password, role, name, avatar) " +
            "VALUES(#{username}, #{password}, #{role}, #{name}, #{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    User findById(Long id);

    @Update("UPDATE sys_user SET password = #{newPassword} WHERE id = #{id}")
    void updatePassword(@Param("id") Long id, @Param("newPassword") String newPassword);

    /**
     * ⭐ 新增：根据用户名更新密码 (解决ID不一致的问题)
     */
    @Update("UPDATE sys_user SET password = #{password} WHERE username = #{username}")
    void updatePasswordByUsername(@Param("username") String username, @Param("password") String password);
}