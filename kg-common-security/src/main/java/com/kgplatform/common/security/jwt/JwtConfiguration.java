package com.kgplatform.common.security.jwt;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置
 * <p>
 * JwtConfiguration配置类
 *
 * @author kg_chen
 * @since 2026-04-23 00:00:00
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfiguration {

    @Bean
    public JwtUtils jwtUtils(JwtProperties jwtProperties) {
        if (jwtProperties.getSecret() == null || jwtProperties.getSecret().isBlank()) {
            throw new IllegalArgumentException("kg.security.jwt.secret 未配置");
        }
        if (jwtProperties.getExpireSeconds() == null || jwtProperties.getExpireSeconds() <= 0) {
            throw new IllegalArgumentException("kg.security.jwt.expire-seconds 必须大于0");
        }
        return new JwtUtils(jwtProperties.getSecret(), jwtProperties.getExpireSeconds());
    }
}

