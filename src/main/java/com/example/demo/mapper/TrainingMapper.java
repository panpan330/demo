package com.example.demo.mapper;

import com.example.demo.entity.Training;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TrainingMapper {

    // 1. 查询所有 (关联查人名)
    @Select("SELECT t.*, p.name as talentName FROM sys_training t " +
            "LEFT JOIN sys_talent p ON t.talent_id = p.id " +
            "ORDER BY t.create_time DESC")
    List<Training> findAll();

    // 2. 查询某人的培训
    @Select("SELECT * FROM sys_training WHERE talent_id = #{talentId} ORDER BY create_time DESC")
    List<Training> findByTalentId(Long talentId);

    // 3. ⭐ 智能插入：只有当该人没有这门课(未完成状态)时才插入，防止重复
    @Insert("INSERT INTO sys_training(talent_id, course_name, status, create_time) " +
            "SELECT #{talentId}, #{courseName}, 'TO_DO', NOW() " +
            "WHERE NOT EXISTS (SELECT 1 FROM sys_training WHERE talent_id=#{talentId} AND course_name=#{courseName})")
    void assign(Training training);

    // 4. 普通新增
    @Insert("INSERT INTO sys_training (talent_id, course_name, status, create_time) " +
            "VALUES (#{talentId}, #{courseName}, 'TO_DO', NOW())")
    void add(Training training);

    // 5. 更新状态
    @Update("UPDATE sys_training SET status = #{status} WHERE id = #{id}")
    void updateStatus(Long id, String status);

    // 6. 更新全部信息
    @Update("UPDATE sys_training SET course_name = #{courseName}, status = #{status} WHERE id = #{id}")
    void update(Training training);

    // 7. 删除
    @Delete("DELETE FROM sys_training WHERE id = #{id}")
    void delete(Long id);
}