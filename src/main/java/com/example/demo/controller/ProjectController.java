package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Project;
import com.example.demo.service.ProjectService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
@CrossOrigin // 允许跨域
public class ProjectController {

    @Resource
    private ProjectService projectService;

    /**
     * 1. 获取所有项目列表
     */
    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(projectService.findAll());
    }

    /**
     * 2. 新增项目
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody Project project) {
        try {
            projectService.addProject(project);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "新增失败：" + e.getMessage());
        }
    }

    /**
     * 3. 更新项目
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody Project project) {
        try {
            projectService.updateProject(project);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "更新失败：" + e.getMessage());
        }
    }

    /**
     * 4. 删除项目
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error("-1", "删除失败：" + e.getMessage());
        }
    }

    /**
     * ⭐ 5. 获取我的科研项目
     * 路径：/api/project/list/my/{talentId}
     */
    @GetMapping("/list/my/{talentId}")
    public Result<?> getMyProjects(@PathVariable Long talentId) {
        return Result.success(projectService.getMyProjects(talentId));
    }
    // ... 现有的代码 ...

    @Resource
    private com.example.demo.mapper.ProjectMapper projectMapper; // 临时直接注入Mapper查任务，或者你写在Service里

    // ⭐ 修复：新增查询项目下任务的接口
    @GetMapping("/tasks/{projectId}")
    public Result<?> getProjectTasks(@PathVariable Long projectId) {
        // 这里需要确保你有 ProjectTask 相关的 Mapper 方法
        // 如果还没写 Task 表的逻辑，暂时返回空列表，防止前端 404 报错
        return Result.success(new java.util.ArrayList<>());
    }
}