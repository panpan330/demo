package com.example.demo.service.impl;

import com.example.demo.entity.Project;
import com.example.demo.mapper.ProjectMapper;
import com.example.demo.service.ProjectService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectMapper projectMapper;

    @Override
    public List<Project> findAll() {
        return projectMapper.findAll();
    }

    @Override
    public void addProject(Project project) {
        // 可以在这里加业务逻辑，比如：如果没有状态，默认设置为 ONGOING
        if (project.getStatus() == null) {
            project.setStatus("ONGOING");
        }
        projectMapper.insert(project);
    }

    @Override
    public void updateProject(Project project) {
        projectMapper.update(project);
    }

    @Override
    public void deleteProject(Long id) {
        projectMapper.deleteById(id);
    }

    /**
     * ⭐ 实现：查询我的项目
     */
    @Override
    public List<Map<String, Object>> getMyProjects(Long talentId) {
        // 直接调用 Mapper
        return projectMapper.findMyProjects(talentId);
    }
}