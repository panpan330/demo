package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Project;
import com.example.demo.service.ProjectService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Resource
    private ProjectService projectService;

    // --- 项目管理 ---

    @GetMapping("/list")
    public Result<?> list() { return Result.success(projectService.findAll()); }

    @PostMapping("/add")
    public Result<?> add(@RequestBody Project project) {
        try { projectService.addProject(project); return Result.success(); } catch (Exception e) { return Result.error("-1", e.getMessage()); }
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody Project project) {
        try { projectService.updateProject(project); return Result.success(); } catch (Exception e) { return Result.error("-1", e.getMessage()); }
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        try { projectService.deleteProject(id); return Result.success(); } catch (Exception e) { return Result.error("-1", e.getMessage()); }
    }

    @GetMapping("/list/my/{talentId}")
    public Result<?> getMyProjects(@PathVariable Long talentId) { return Result.success(projectService.getMyProjects(talentId)); }

    // 占位接口
    @GetMapping("/tasks/{projectId}")
    public Result<?> getProjectTasks(@PathVariable Long projectId) { return Result.success(new java.util.ArrayList<>()); }


    // --- ⭐ 成员管理 (现在全部调用 Service) ---

    @GetMapping("/members/{projectId}")
    public Result<?> getMembers(@PathVariable Long projectId) {
        return Result.success(projectService.getProjectMembers(projectId));
    }

    @PostMapping("/member/add")
    public Result<?> addMember(@RequestBody Map<String, Object> params) {
        try {
            Long projectId = Long.valueOf(params.get("projectId").toString());
            Long talentId = Long.valueOf(params.get("talentId").toString());
            String role = (String) params.get("roleInProj");

            projectService.addProjectMember(projectId, talentId, role);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", e.getMessage());
        }
    }

    @DeleteMapping("/member/remove/{id}")
    public Result<?> removeMember(@PathVariable Long id) {
        try {
            projectService.removeProjectMember(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "移除失败");
        }
    }
}