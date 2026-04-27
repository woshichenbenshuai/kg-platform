package com.kgplatform.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 认证服务启动
 * <p>
 * KgAuthApplication启动类
 *
 * @author kg_chen
 * @since 2026-04-22 18:50:54
 */
@MapperScan("com.kgplatform.auth.mapper")
@SpringBootApplication(scanBasePackages = "com.kgplatform")
public class KgAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(KgAuthApplication.class, args);
    }
}