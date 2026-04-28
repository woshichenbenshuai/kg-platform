package com.kgplatform.business.kinder.client.remote.feign;

import com.kgplatform.common.web.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * System 用户租户 Feign API
 */
@FeignClient(
        name = "${kg.system-client.service-name:kg-system}",
        url = "${kg.system-client.base-url:http://127.0.0.1:8082}",
        contextId = "systemUserTenantFeignApi",
        path = "/internal/system/user-tenants"
)
public interface SystemUserTenantFeignApi {

    @GetMapping("/users/{userId}/default-tenant-id")
    Result<Long> getDefaultTenantIdByUserId(@PathVariable("userId") Long userId);
}
