package com.kgplatform.system.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 系统Web配置
 * <p>
 * SystemWebMvcConfiguration配置类
 * @author kg_chen
 * @since 2026-04-23 08:59:19
 */
@Configuration
public class SystemWebMvcConfiguration implements WebMvcConfigurer {

    private final SystemAuthInterceptor systemAuthInterceptor;

    public SystemWebMvcConfiguration(SystemAuthInterceptor systemAuthInterceptor) {
        this.systemAuthInterceptor = systemAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(systemAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/actuator/**", "/error");
    }
}
