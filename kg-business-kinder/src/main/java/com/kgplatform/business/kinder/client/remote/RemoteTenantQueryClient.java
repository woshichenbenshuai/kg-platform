package com.kgplatform.business.kinder.client.remote;

import com.kgplatform.business.kinder.client.TenantQueryClient;
import com.kgplatform.business.kinder.client.remote.feign.SystemTenantFeignApi;
import com.kgplatform.business.kinder.client.remote.feign.SystemUserTenantFeignApi;
import com.kgplatform.business.kinder.domain.dto.TenantDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 远程租户查询 Client
 */
@Component
@ConditionalOnProperty(prefix = "kg.system-client", name = "mode", havingValue = "feign")
public class RemoteTenantQueryClient extends RemoteClientSupport implements TenantQueryClient {

    private final SystemTenantFeignApi systemTenantFeignApi;
    private final SystemUserTenantFeignApi systemUserTenantFeignApi;

    public RemoteTenantQueryClient(SystemTenantFeignApi systemTenantFeignApi,
                                   SystemUserTenantFeignApi systemUserTenantFeignApi) {
        this.systemTenantFeignApi = systemTenantFeignApi;
        this.systemUserTenantFeignApi = systemUserTenantFeignApi;
    }

    @Override
    public TenantDto getTenantById(Long tenantId) {
        return unwrap(systemTenantFeignApi.getTenantById(tenantId), "远程租户不存在");
    }

    @Override
    public Long getDefaultTenantIdByUserId(Long userId) {
        return unwrap(systemUserTenantFeignApi.getDefaultTenantIdByUserId(userId), "远程默认租户不存在");
    }
}
