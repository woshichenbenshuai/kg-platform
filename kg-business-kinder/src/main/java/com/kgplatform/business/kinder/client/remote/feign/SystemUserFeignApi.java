package com.kgplatform.business.kinder.client.remote.feign;

import com.kgplatform.common.web.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * System 用户 Feign API
 */
@FeignClient(
        name = "${kg.system-client.service-name:kg-system}",
        url = "${kg.system-client.base-url:http://127.0.0.1:8082}",
        contextId = "systemUserFeignApi",
        path = "/internal/system/users"
)
public interface SystemUserFeignApi {

    @GetMapping("/{userId}/nickname")
    Result<String> getNicknameByUserId(@PathVariable("userId") Long userId);
}
