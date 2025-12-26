package com.example.demo.mapper;

import com.example.demo.entity.Talent;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TalentMapper {

    @Select("SELECT * FROM sys_talent")
    List<Talent> findAll();

    // ⭐ 检查：这里必须要有 lng, lat
    @Insert("INSERT INTO sys_talent(" +
            "name, gender, role, birthday, education, major, phone, email, " +
            "id_card, address, lng, lat, cs_score, med_score, user_id" +
            ") VALUES (" +
            "#{name}, #{gender}, #{role}, #{birthday}, #{education}, #{major}, #{phone}, #{email}, " +
            "#{idCard}, #{address}, #{lng}, #{lat}, #{csScore}, #{medScore}, #{userId}" +
            ")")
    void add(Talent talent);

    // ⭐ 检查：这里也必须要有 lng, lat
    @Update("UPDATE sys_talent SET " +
            "name=#{name}, gender=#{gender}, role=#{role}, birthday=#{birthday}, " +
            "education=#{education}, major=#{major}, phone=#{phone}, email=#{email}, " +
            "id_card=#{idCard}, address=#{address}, lng=#{lng}, lat=#{lat}, " +
            "cs_score=#{csScore}, med_score=#{medScore} " +
            "WHERE id = #{id}")
    void update(Talent talent);

    @Delete("DELETE FROM sys_talent WHERE id = #{id}")
    void deleteById(Long id);

    @Select("SELECT * FROM sys_talent WHERE user_id = #{userId}")
    Talent selectByUserId(Long userId);
}