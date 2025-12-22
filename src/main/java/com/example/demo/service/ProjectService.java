package com.example.demo.service;

import com.example.demo.entity.Project;
import com.example.demo.entity.ProjectTask;
import java.util.List;

public interface ProjectService {

    // 查询所有项目
    List<Project> getAllProjects();

    // 查询项目下的任务
    List<ProjectTask> getTasks(Long projectId);

    // ⭐ 新增：立项
    void addProject(Project project);

    // ⭐ 修改：更新项目信息
    void updateProject(Project project);

    // ⭐ 删除：撤项
    void deleteProject(Long id);
}