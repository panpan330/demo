package com.example.demo.common;

import lombok.Data;

/**
 * 统一API响应结果封装类
 * @param <T> 数据载荷的类型
 */
@Data
public class Result<T> {

    private String code; // 状态码 (200=成功, 500=失败)
    private String msg;  // 提示信息
    private T data;      // 返回的具体数据 (对象、列表等)

    // 无参构造
    public Result() {}

    // 全参构造
    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // ============================
    // 快速构建成功结果的方法
    // ============================

    // 成功，不带数据
    public static <T> Result<T> success() {
        return new Result<>("200", "操作成功", null);
    }

    // 成功，带数据
    public static <T> Result<T> success(T data) {
        return new Result<>("200", "操作成功", data);
    }

    // ============================
    // 快速构建失败结果的方法
    // ============================

    // 失败，带自定义消息
    public static <T> Result<T> error(String msg) {
        return new Result<>("500", msg, null);
    }

    // 失败，带自定义状态码和消息
    public static <T> Result<T> error(String code, String msg) {
        return new Result<>(code, msg, null);
    }
}