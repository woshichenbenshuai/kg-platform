package com.kgplatform.business.kinder.config;

import com.kgplatform.business.kinder.client.remote.feign.SystemTenantDbConfigFeignApi;
import com.kgplatform.business.kinder.client.remote.feign.SystemTenantFeignApi;
import com.kgplatform.business.kinder.client.remote.feign.SystemUserFeignApi;
import com.kgplatform.business.kinder.client.remote.feign.SystemUserTenantFeignApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * System Feign Client 配置
 */
@Configuration
@ConditionalOnProperty(prefix = "kg.system-client", name = "mode", havingValue = "feign")
@EnableFeignClients(clients = {
        SystemTenantFeignApi.class,
        SystemUserFeignApi.class,
        SystemUserTenantFeignApi.class,
        SystemTenantDbConfigFeignApi.class
})
public class SystemClientFeignConfiguration {
}
