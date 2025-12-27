package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// ⭐ 必须引入这个类，否则下面的 exclude 会报错
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * exclude = {SecurityAutoConfiguration.class}
 * 作用：告诉 Spring Boot "不要自动开启安全拦截"，
 * 这样我们写的 login 接口才能正常工作，而不是被重定向到自带的登录页。
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}