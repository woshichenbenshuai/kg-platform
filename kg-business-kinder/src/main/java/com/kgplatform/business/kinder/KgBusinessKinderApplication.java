package com.kgplatform.business.kinder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 幼儿园业务服务启动
 * <p>
 * KgBusinessKinderApplication启动类
 * @author kg_chen
 * @since 2026-04-23 10:00:00
 */
@MapperScan("com.kgplatform.business.kinder.mapper")
@SpringBootApplication(scanBasePackages = "com.kgplatform")
public class KgBusinessKinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(KgBusinessKinderApplication.class, args);
    }
}
