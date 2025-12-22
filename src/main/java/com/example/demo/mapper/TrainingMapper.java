package com.example.demo.mapper;

import com.example.demo.entity.Training; // 假设你有这个实体，没有的话下面会补
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface TrainingMapper {

    // 查 (已有 - 关联查询显示人名)
    @Select("SELECT t.*, p.name as talentName FROM sys_training t " +
            "LEFT JOIN sys_talent p ON t.talent_id = p.id " +
            "ORDER BY t.create_time DESC")
    List<Training> findAll();  // 👈 这里改了

    // 查某个人的 (已有)
    @Select("SELECT * FROM sys_training WHERE talent_id = #{talentId}")
    List<Training> findByTalentId(Long talentId);

    // ⭐ 新增: 指派培训
    @Insert("INSERT INTO sys_training (talent_id, course_name, status, create_time) " +
            "VALUES (#{talentId}, #{courseName}, 'TO_DO', NOW())")
    void add(Training training);

    // ⭐ 修改: 更新课程名或状态
    @Update("UPDATE sys_training SET course_name = #{courseName}, status = #{status} WHERE id = #{id}")
    void update(Training training);

    // ⭐ 删除: 移除记录
    @Delete("DELETE FROM sys_training WHERE id = #{id}")
    void delete(Long id);
}