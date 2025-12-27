package com.example.demo.service;

import com.example.demo.entity.Project;
import java.util.List;
import java.util.Map;

public interface ProjectService {

    // --- 原有项目管理接口 ---
    List<Project> findAll();
    void addProject(Project project);
    void updateProject(Project project);
    void deleteProject(Long id);
    List<Map<String, Object>> getMyProjects(Long talentId);

    // --- ⭐ 新增：成员管理接口 ---

    // 1. 获取某项目的成员列表
    List<Map<String, Object>> getProjectMembers(Long projectId);

    // 2. 添加成员
    void addProjectMember(Long projectId, Long talentId, String role);

    // 3. 移除成员
    void removeProjectMember(Long id);
}