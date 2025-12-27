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
    public List<Project> findAll() { return projectMapper.findAll(); }

    @Override
    public void addProject(Project project) {
        if (project.getStatus() == null) project.setStatus("ONGOING");
        projectMapper.insert(project);
    }

    @Override
    public void updateProject(Project project) { projectMapper.update(project); }

    @Override
    public void deleteProject(Long id) { projectMapper.deleteById(id); }

    @Override
    public List<Map<String, Object>> getMyProjects(Long talentId) {
        return projectMapper.findMyProjects(talentId);
    }

    // --- ⭐ 成员管理逻辑实现 ---

    @Override
    public List<Map<String, Object>> getProjectMembers(Long projectId) {
        return projectMapper.findMembersByProjectId(projectId);
    }

    @Override
    public void addProjectMember(Long projectId, Long talentId, String role) {
        // 1. 业务逻辑：查重 (Service 层负责校验)
        int count = projectMapper.checkMemberExist(projectId, talentId);
        if (count > 0) {
            throw new RuntimeException("该成员已在项目中，请勿重复添加");
        }
        // 2. 插入
        projectMapper.addMember(projectId, talentId, role);
    }

    @Override
    public void removeProjectMember(Long id) {
        projectMapper.removeMember(id);
    }
}