package com.kgplatform.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 网关服务启动
 * <p>
 * KgGatewayApplication启动类
 * @author kg_chen
 * @since 2026-04-23 10:00:00
 */
@SpringBootApplication(scanBasePackages = "com.kgplatform")
public class KgGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(KgGatewayApplication.class, args);
    }
}
