package com.kgplatform.business.kinder.client.remote;

import com.kgplatform.business.kinder.client.TenantDataSourceConfigClient;
import com.kgplatform.business.kinder.client.dto.TenantDataSourceConfigDto;
import com.kgplatform.business.kinder.client.remote.feign.SystemTenantDbConfigFeignApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 远程租户库配置 Client
 */
@Component
@ConditionalOnProperty(prefix = "kg.system-client", name = "mode", havingValue = "feign")
public class RemoteTenantDataSourceConfigClient extends RemoteClientSupport implements TenantDataSourceConfigClient {

    private final SystemTenantDbConfigFeignApi systemTenantDbConfigFeignApi;

    public RemoteTenantDataSourceConfigClient(SystemTenantDbConfigFeignApi systemTenantDbConfigFeignApi) {
        this.systemTenantDbConfigFeignApi = systemTenantDbConfigFeignApi;
    }

    @Override
    public TenantDataSourceConfigDto getConfigByTenantId(Long tenantId) {
        return unwrap(systemTenantDbConfigFeignApi.getConfigByTenantId(tenantId), "远程租户数据库配置不存在");
    }
}
