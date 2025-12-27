package com.example.demo.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AssetDevice {
    private Long id;
    private Long categoryId;
    private String deviceName;
    private String deviceCode;

    // 状态: IDLE(空闲)/BORROWED(借出)/BROKEN(维修)
    private String status;

    private BigDecimal price;

    private LocalDateTime createTime;

    /**
     * ⭐ 新增字段：当前借用人ID
     * 对应数据库里的 borrower_id 字段
     */
    private Long borrowerId;
}