package com.example.demo.mapper;

import com.example.demo.entity.Talent;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface TalentMapper {

    // 1. 查询所有 (如果你想显示关联的账号名，这里以后可能要改关联查询，现在先查基本信息)
    @Select("SELECT * FROM sys_talent")
    List<Talent> findAll();

    // 2. ⭐⭐ 关键修复在这里！⭐⭐
    // 以前可能漏了 "user_id" 和 "#{userId}"
    // 现在必须加上，否则 Service 层辛苦生成的 ID 存不进去！
    @Insert("INSERT INTO sys_talent(name, role, cs_score, med_score, user_id) " +
            "VALUES(#{name}, #{role}, #{csScore}, #{medScore}, #{userId})")
    void add(Talent talent);

    // 3. 删除
    @Delete("DELETE FROM sys_talent WHERE id = #{id}")
    void deleteById(Long id);

    // 4. 根据 userId 查档案 (个人中心用)
    @Select("SELECT * FROM sys_talent WHERE user_id = #{userId}")
    Talent selectByUserId(Long userId);
}