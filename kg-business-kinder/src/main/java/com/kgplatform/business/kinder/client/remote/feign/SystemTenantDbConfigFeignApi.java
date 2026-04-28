package com.kgplatform.business.kinder.client.remote.feign;

import com.kgplatform.business.kinder.client.dto.TenantDataSourceConfigDto;
import com.kgplatform.common.web.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * System 租户库配置 Feign API
 */
@FeignClient(
        name = "${kg.system-client.service-name:kg-system}",
        url = "${kg.system-client.base-url:http://127.0.0.1:8082}",
        contextId = "systemTenantDbConfigFeignApi",
        path = "/internal/system/tenant-db-configs"
)
public interface SystemTenantDbConfigFeignApi {

    @GetMapping("/tenant/{tenantId}")
    Result<TenantDataSourceConfigDto> getConfigByTenantId(@PathVariable("tenantId") Long tenantId);
}
