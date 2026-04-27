package com.kgplatform.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * KgSystemApplication
 * <p>
 * KgSystemApplication启动类
 *
 * @author kg_chen
 * @since 2026-04-22 18:50:54
 */
@MapperScan("com.kgplatform.system")
@SpringBootApplication(scanBasePackages = "com.kgplatform")
public class KgSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(KgSystemApplication.class, args);
    }
}
