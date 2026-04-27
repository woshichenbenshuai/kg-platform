package com.kgplatform.common.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT配置属性
 * <p>
 * JwtProperties业务类
 *
 * @author kg_chen
 * @since 2026-04-23 00:00:00
 */
@ConfigurationProperties(prefix = "kg.security.jwt")
public class JwtProperties {

    private String secret;

    private Long expireSeconds = 86400L;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(Long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }
}

