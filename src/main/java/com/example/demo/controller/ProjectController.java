package com.example.demo.controller;

import com.example.demo.entity.Project;
import com.example.demo.entity.ProjectTask;
import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectService projectService; // 假设你在 Service 里也加了对应方法

    @GetMapping("/list")
    public List<Project> list() { return projectService.getAllProjects(); }

    @GetMapping("/tasks/{projectId}")
    public List<ProjectTask> tasks(@PathVariable Long projectId) { return projectService.getTasks(projectId); }

    // ⭐ 新增接口
    @PostMapping("/add")
    public String add(@RequestBody Project project) {
        projectService.addProject(project); // 请确保 Service 层有这个方法
        return "success";
    }

    // ⭐ 修改接口
    @PutMapping("/update")
    public String update(@RequestBody Project project) {
        projectService.updateProject(project); // 请确保 Service 层有这个方法
        return "success";
    }

    // ⭐ 删除接口
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        projectService.deleteProject(id); // 请确保 Service 层有这个方法
        return "success";
    }
}