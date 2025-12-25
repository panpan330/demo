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
    private String gender; // 新增

    @ExcelProperty("年龄") // 数据库没存年龄，但可以通过出生日期算，这里暂时留字段方便前端传，或者存出生日期
    @ExcelIgnore // 数据库没这个字段，暂时忽略导出，或者后续我们在 getter 里计算
    private Integer age;

    @ExcelProperty("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") // 格式化日期
    private Date birthday; // 新增

    @ExcelProperty("角色")
    private String role;

    @ExcelProperty("学历")
    private String education; // 新增

    @ExcelProperty("专业")
    private String major; // 新增

    @ExcelProperty("手机号")
    private String phone; // 新增

    @ExcelProperty("邮箱")
    private String email; // 新增

    @ExcelProperty("身份证号")
    private String idCard;

    @ExcelProperty("家庭地址")
    private String address;

    @ExcelProperty("计算机能力")
    private Integer csScore;

    @ExcelProperty("医学能力")
    private Integer medScore;

    @ExcelIgnore // 导出时不需要显示关联的用户ID
    private Long userId;
}