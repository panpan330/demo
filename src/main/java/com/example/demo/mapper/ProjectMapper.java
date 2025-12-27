package com.example.demo.mapper;

import com.example.demo.entity.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectMapper {

    /**
     * 1. 查询所有项目 (管理员用)
     */
    @Select("SELECT * FROM proj_project ORDER BY id DESC")
    List<Project> findAll();

    /**
     * 2. 新增项目
     */
    @Insert("INSERT INTO proj_project(name, principal, status, total_budget, start_date, end_date, description) " +
            "VALUES(#{name}, #{principal}, #{status}, #{totalBudget}, #{startDate}, #{endDate}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Project project);

    /**
     * 3. 更新项目
     */
    @Update("UPDATE proj_project SET name=#{name}, principal=#{principal}, status=#{status}, " +
            "total_budget=#{totalBudget}, start_date=#{startDate}, end_date=#{endDate}, description=#{description} " +
            "WHERE id = #{id}")
    void update(Project project);

    /**
     * 4. 删除项目
     */
    @Delete("DELETE FROM proj_project WHERE id = #{id}")
    void deleteById(Long id);

    /**
     * ⭐ 5. 查询某个人才参与的项目 (学生/个人中心用)
     * 返回 Map 是因为我们要多返回一个 'myRole' 字段，这个字段不在 Project 实体里
     */
    @Select("SELECT p.*, m.role_in_proj as myRole " +
            "FROM proj_project p " +
            "JOIN proj_member m ON p.id = m.project_id " +
            "WHERE m.talent_id = #{talentId} " +
            "ORDER BY p.start_date DESC")
    List<Map<String, Object>> findMyProjects(Long talentId);
}