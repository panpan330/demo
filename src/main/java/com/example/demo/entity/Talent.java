package com.example.demo.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class Talent {
    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("性别")
    private String gender;

    @ExcelProperty("年龄")
    @ExcelIgnore
    private Integer age;

    @ExcelProperty("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    @ExcelProperty("角色")
    private String role;

    @ExcelProperty("学历")
    private String education;

    @ExcelProperty("专业")
    private String major;

    @ExcelProperty("手机号")
    private String phone;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("身份证号")
    private String idCard;

    @ExcelProperty("家庭地址")
    private String address;

    @ExcelIgnore
    private Double lng;

    @ExcelIgnore
    private Double lat;

    @ExcelProperty("计算机能力")
    private Integer csScore;

    @ExcelProperty("医学能力")
    private Integer medScore;

    @ExcelIgnore
    private Long userId;

    // ⭐⭐ 新增：用于接收 sys_user 表查询出来的登录账号
    @ExcelIgnore // 不导出到 Excel，仅用于前端显示和重置密码
    private String username;
}