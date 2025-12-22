package com.example.demo.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Talent {
    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("角色")
    private String role;

    @ExcelProperty("计算机能力")
    private Integer csScore;

    @ExcelProperty("医学能力")
    private Integer medScore;

    private Long userId;

    // 不想导出的字段加 @ExcelIgnore
   //@ExcelIgnore
   //private String password;
}