package com.example.demo.entity;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjectTask {
    private Long id;
    private Long projectId;
    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer progress;
}