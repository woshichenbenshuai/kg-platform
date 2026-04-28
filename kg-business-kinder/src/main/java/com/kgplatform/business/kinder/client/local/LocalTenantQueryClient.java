package com.kgplatform.business.kinder.client.local;

import com.kgplatform.business.kinder.client.TenantQueryClient;
import com.kgplatform.business.kinder.domain.dto.TenantDto;
import com.kgplatform.business.kinder.mapper.master.MasterTenantMapper;
import com.kgplatform.business.kinder.mapper.master.MasterUserTenantMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 本地租户查询 Client
 */
@Component
@ConditionalOnProperty(prefix = "kg.system-client", name = "mode", havingValue = "local", matchIfMissing = true)
public class LocalTenantQueryClient implements TenantQueryClient {

    private final MasterTenantMapper masterTenantMapper;
    private final MasterUserTenantMapper masterUserTenantMapper;

    public LocalTenantQueryClient(MasterTenantMapper masterTenantMapper,
                                  MasterUserTenantMapper masterUserTenantMapper) {
        this.masterTenantMapper = masterTenantMapper;
        this.masterUserTenantMapper = masterUserTenantMapper;
    }

    @Override
    public TenantDto getTenantById(Long tenantId) {
        return masterTenantMapper.selectEnabledById(tenantId);
    }

    @Override
    public Long getDefaultTenantIdByUserId(Long userId) {
        return masterUserTenantMapper.selectDefaultTenantIdByUserId(userId);
    }
}
