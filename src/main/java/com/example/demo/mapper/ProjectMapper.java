package com.example.demo.mapper;

import com.example.demo.entity.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectMapper {

    // ... 原有的 findAll, insert, update, deleteById, findMyProjects 保持不变 ...
    @Select("SELECT * FROM proj_project ORDER BY id DESC")
    List<Project> findAll();

    @Insert("INSERT INTO proj_project(name, principal, status, total_budget, start_date, end_date, description) " +
            "VALUES(#{name}, #{principal}, #{status}, #{totalBudget}, #{startDate}, #{endDate}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Project project);

    @Update("UPDATE proj_project SET name=#{name}, principal=#{principal}, status=#{status}, " +
            "total_budget=#{totalBudget}, start_date=#{startDate}, end_date=#{endDate}, description=#{description} " +
            "WHERE id = #{id}")
    void update(Project project);

    @Delete("DELETE FROM proj_project WHERE id = #{id}")
    void deleteById(Long id);

    @Select("SELECT p.*, m.role_in_proj as myRole FROM proj_project p JOIN proj_member m ON p.id = m.project_id WHERE m.talent_id = #{talentId} ORDER BY p.start_date DESC")
    List<Map<String, Object>> findMyProjects(Long talentId);

    // ⭐⭐ 新增：成员管理相关 SQL ⭐⭐

    // 1. 查看某个项目有哪些成员 (连表查名字)
    @Select("SELECT m.id, m.talent_id, m.role_in_proj, t.name as talentName " +
            "FROM proj_member m " +
            "LEFT JOIN sys_talent t ON m.talent_id = t.id " +
            "WHERE m.project_id = #{projectId}")
    List<Map<String, Object>> findMembersByProjectId(Long projectId);

    // 2. 添加成员
    @Insert("INSERT INTO proj_member(project_id, talent_id, role_in_proj) VALUES(#{projectId}, #{talentId}, #{roleInProj})")
    void addMember(@Param("projectId") Long projectId, @Param("talentId") Long talentId, @Param("roleInProj") String roleInProj);

    // 3. 移除成员
    @Delete("DELETE FROM proj_member WHERE id = #{id}")
    void removeMember(Long id);

    // 4. 检查是否已存在 (防止重复添加)
    @Select("SELECT count(*) FROM proj_member WHERE project_id = #{projectId} AND talent_id = #{talentId}")
    int checkMemberExist(@Param("projectId") Long projectId, @Param("talentId") Long talentId);
}