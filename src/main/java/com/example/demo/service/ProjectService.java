package com.example.demo.service;

import com.example.demo.entity.Project;
import java.util.List;
import java.util.Map;

public interface ProjectService {

    // 管理员功能
    List<Project> findAll();
    void addProject(Project project);
    void updateProject(Project project);
    void deleteProject(Long id);

    // ⭐ 个人中心功能：查询我的项目
    List<Map<String, Object>> getMyProjects(Long talentId);
}