package com.example.demo.entity;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Project {
    private Long id;
    private String name;
    private String principal;
    private String status;
    private BigDecimal totalBudget;
    private LocalDate startDate;
    private LocalDate endDate;
}