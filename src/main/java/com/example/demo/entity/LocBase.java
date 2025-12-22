package com.example.demo.entity;

import lombok.Data;
import java.math.BigDecimal; // 注意：经纬度要用 BigDecimal 保证精度

@Data
public class LocBase {
    private Long id;
    private String name;
    private String type;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer capacity;
    private String description;
}