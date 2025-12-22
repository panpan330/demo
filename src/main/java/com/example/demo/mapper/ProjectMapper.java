package com.example.demo.mapper;

import com.example.demo.entity.Project;
import com.example.demo.entity.ProjectTask;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ProjectMapper {
    // 查 (已有)
    @Select("SELECT * FROM proj_project ORDER BY start_date DESC")
    List<Project> findAllProjects();

    @Select("SELECT * FROM proj_task WHERE project_id = #{projectId} ORDER BY start_date ASC")
    List<ProjectTask> findTasksByProjectId(Long projectId);

    // ⭐ 新增：立项
    @Insert("INSERT INTO proj_project (name, principal, status, total_budget, start_date, end_date, description) " +
            "VALUES (#{name}, #{principal}, #{status}, #{totalBudget}, #{startDate}, #{endDate}, #{description})")
    void add(Project project);

    // ⭐ 修改：变更项目信息
    @Update("UPDATE proj_project SET name=#{name}, principal=#{principal}, status=#{status}, " +
            "total_budget=#{totalBudget}, start_date=#{startDate}, end_date=#{endDate} WHERE id=#{id}")
    void update(Project project);

    // ⭐ 删除：撤项
    @Delete("DELETE FROM proj_project WHERE id = #{id}")
    void delete(Long id);
}