package com.kgplatform.business.kinder.client.local;

import com.kgplatform.business.kinder.client.TenantDataSourceConfigClient;
import com.kgplatform.business.kinder.client.dto.TenantDataSourceConfigDto;
import com.kgplatform.business.kinder.mapper.master.MasterTenantDbConfigMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 本地租户数据源配置 Client
 */
@Component
@ConditionalOnProperty(prefix = "kg.system-client", name = "mode", havingValue = "local", matchIfMissing = true)
public class LocalTenantDataSourceConfigClient implements TenantDataSourceConfigClient {

    private final MasterTenantDbConfigMapper masterTenantDbConfigMapper;

    public LocalTenantDataSourceConfigClient(MasterTenantDbConfigMapper masterTenantDbConfigMapper) {
        this.masterTenantDbConfigMapper = masterTenantDbConfigMapper;
    }

    @Override
    public TenantDataSourceConfigDto getConfigByTenantId(Long tenantId) {
        return masterTenantDbConfigMapper.selectEnabledByTenantId(tenantId);
    }
}
