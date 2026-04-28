package com.kgplatform.business.kinder.client.remote.feign;

import com.kgplatform.business.kinder.domain.dto.TenantDto;
import com.kgplatform.common.web.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * System 租户 Feign API
 */
@FeignClient(
        name = "${kg.system-client.service-name:kg-system}",
        url = "${kg.system-client.base-url:http://127.0.0.1:8082}",
        contextId = "systemTenantFeignApi",
        path = "/internal/system/tenants"
)
public interface SystemTenantFeignApi {

    @GetMapping("/{tenantId}")
    Result<TenantDto> getTenantById(@PathVariable("tenantId") Long tenantId);
}
