package com.example.demo.mapper;

import com.example.demo.entity.Talent;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TalentMapper {

    @Select("SELECT * FROM sys_talent")
    List<Talent> findAll();

    // ⭐ 修改 Insert：加入 id_card 和 address
    @Insert("INSERT INTO sys_talent(name, role, cs_score, med_score, user_id, gender, phone, education, major, email, birthday, id_card, address) " +
            "VALUES(#{name}, #{role}, #{csScore}, #{medScore}, #{userId}, #{gender}, #{phone}, #{education}, #{major}, #{email}, #{birthday}, #{idCard}, #{address})")
    void add(Talent talent);

    // ⭐ 修改 Update：加入 id_card 和 address
    @Update("UPDATE sys_talent SET name=#{name}, gender=#{gender}, birthday=#{birthday}, " +
            "education=#{education}, major=#{major}, phone=#{phone}, email=#{email}, " +
            "id_card=#{idCard}, address=#{address}, " + // <-- 加上这一行
            "role=#{role}, cs_score=#{csScore}, med_score=#{medScore} " +
            "WHERE id = #{id}")
    void update(Talent talent);

    @Delete("DELETE FROM sys_talent WHERE id = #{id}")
    void deleteById(Long id);

    @Select("SELECT * FROM sys_talent WHERE user_id = #{userId}")
    Talent selectByUserId(Long userId);
}