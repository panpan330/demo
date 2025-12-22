package com.example.demo.service.impl;

import com.example.demo.entity.Project;
import com.example.demo.entity.ProjectTask;
import com.example.demo.mapper.ProjectMapper;
import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 引入事务管理

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public List<Project> getAllProjects() {
        return projectMapper.findAllProjects();
    }

    @Override
    public List<ProjectTask> getTasks(Long projectId) {
        return projectMapper.findTasksByProjectId(projectId);
    }

    @Override
    public void addProject(Project project) {
        // 这里可以加逻辑：比如判断项目名是否重复，或者设置默认状态
        if (project.getStatus() == null) {
            project.setStatus("ONGOING");
        }
        projectMapper.add(project);
    }

    @Override
    public void updateProject(Project project) {
        projectMapper.update(project);
    }

    @Override
    @Transactional // ⭐ 加上事务注解：保证删除项目和删除关联任务要么都成功，要么都失败
    public void deleteProject(Long id) {
        // 1. 先删除项目主表记录
        projectMapper.delete(id);

        // 2. (可选完善) 最好同时也删除该项目下的任务，防止产生脏数据
        // 如果你的 ProjectMapper 里还没有 deleteTasksByProjectId 方法，暂时可以不写这一行
        // projectMapper.deleteTasksByProjectId(id);
    }
}