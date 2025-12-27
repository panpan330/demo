package com.example.demo.mapper;

import com.example.demo.entity.Training;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TrainingMapper {

    // 1. 查询所有 (带上人才名字)
    @Select("SELECT t.*, tl.name as talentName FROM sys_training t LEFT JOIN sys_talent tl ON t.talent_id = tl.id ORDER BY t.create_time DESC")
    List<Training> findAll();

    // 2. 根据人才ID查询
    @Select("SELECT * FROM sys_training WHERE talent_id = #{talentId} ORDER BY create_time DESC")
    List<Training> findByTalentId(Long talentId);

    // 3. 新增 (对应 Service 里的 add/assign)
    @Insert("INSERT INTO sys_training(talent_id, course_name, status, create_time) VALUES(#{talentId}, #{courseName}, #{status}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Training training);

    // 4. 更新 (对应 Service 里的 update)
    @Update("UPDATE sys_training SET status = #{status} WHERE id = #{id}")
    void update(Training training);

    // 5. 删除 (对应 Service 里的 delete)
    @Delete("DELETE FROM sys_training WHERE id = #{id}")
    void deleteById(Long id);
}