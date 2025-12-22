package com.example.demo.mapper;

import com.example.demo.entity.Talent;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface TalentMapper {
    @Select("SELECT * FROM sys_talent")
    List<Talent> findAll();

    // 新增：插入一条数据
    @Insert("INSERT INTO sys_talent (name, role, cs_score, med_score) VALUES (#{name}, #{role}, #{csScore}, #{medScore})")
    void add(Talent talent);

    // 新增：根据ID删除数据
    @Delete("DELETE FROM sys_talent WHERE id = #{id}")
    void deleteById(Long id);

    @Select("SELECT * FROM sys_talent WHERE user_id = #{userId}")
    Talent selectByUserId(Long userId);
}