package com.example.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AssetDevice {
    @ExcelProperty("设备ID")
    private Long id;

    // 这一列我们导出的时候通常希望显示“分类名”，而不是ID
    // 但为了简单，先导出ID，或者你可以让 SQL 查出来 categoryName 填进去
    @ExcelProperty("分类ID")
    private Long categoryId;

    @ExcelProperty("设备名称")
    private String deviceName;

    @ExcelProperty("资产编号")
    private String deviceCode;

    @ExcelProperty("当前状态")
    private String status;

    @ExcelProperty("采购单价")
    private BigDecimal price;

    @ExcelProperty("所属分类")
    private String categoryName;
}