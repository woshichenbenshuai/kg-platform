package com.kgplatform.auth.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 认证服务 Web 配置
 */
@Configuration
public class AuthWebMvcConfiguration implements WebMvcConfigurer {

    private final AuthCurrentUserInterceptor authCurrentUserInterceptor;

    public AuthWebMvcConfiguration(AuthCurrentUserInterceptor authCurrentUserInterceptor) {
        this.authCurrentUserInterceptor = authCurrentUserInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authCurrentUserInterceptor)
                .addPathPatterns("/auth/current-user")
                .excludePathPatterns("/actuator/**", "/error");
    }
}
