package com.kgplatform.business.kinder.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 园所业务 Web 配置
 */
@Configuration
public class BusinessWebMvcConfiguration implements WebMvcConfigurer {

    private final BusinessTenantInterceptor businessTenantInterceptor;

    public BusinessWebMvcConfiguration(BusinessTenantInterceptor businessTenantInterceptor) {
        this.businessTenantInterceptor = businessTenantInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(businessTenantInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/actuator/**", "/error");
    }
}
