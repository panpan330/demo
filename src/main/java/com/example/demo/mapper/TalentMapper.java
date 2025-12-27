package com.example.demo.mapper;

import com.example.demo.entity.Talent;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TalentMapper {

    /**
     * ⭐ 修改 1：使用 user_id 字段进行关联
     * 只有当 sys_talent 表里有 user_id 字段，且该字段存的是 sys_user 的 id 时，这里才能查出数据
     */
    @Select("SELECT t.*, u.username " +
            "FROM sys_talent t " +
            "LEFT JOIN sys_user u ON t.user_id = u.id")
    List<Talent> findAll();

    @Select("SELECT * FROM sys_talent WHERE id = #{id}")
    Talent findById(Long id);

    /**
     * ⭐ 修改 2：根据 user_id 查询档案
     * 用于“个人成长中心”，根据当前登录用户的 ID 找到他的档案
     */
    @Select("SELECT * FROM sys_talent WHERE user_id = #{userId}")
    Talent selectByUserId(Long userId);

    @Delete("DELETE FROM sys_talent WHERE id = #{id}")
    void deleteById(Long id);

    /**
     * ⭐ 修改 3：新增时，把 user_id 也存进去
     * (确保你的 Talent.java 实体类里有 userId 字段)
     */
    @Insert("INSERT INTO sys_talent(user_id, name, gender, birthday, id_card, address, education, major, phone, email, role, cs_score, med_score) " +
            "VALUES(#{userId}, #{name}, #{gender}, #{birthday}, #{idCard}, #{address}, #{education}, #{major}, #{phone}, #{email}, #{role}, #{csScore}, #{medScore})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(Talent talent);

    /**
     * ⭐ 修改 4：更新时，允许更新 user_id (如果需要的话)
     */
    @Update("UPDATE sys_talent SET user_id=#{userId}, name=#{name}, gender=#{gender}, birthday=#{birthday}, id_card=#{idCard}, " +
            "address=#{address}, education=#{education}, major=#{major}, phone=#{phone}, email=#{email}, " +
            "role=#{role}, cs_score=#{csScore}, med_score=#{medScore} WHERE id=#{id}")
    void update(Talent talent);
}