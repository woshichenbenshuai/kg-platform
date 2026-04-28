package com.kgplatform.business.kinder.client;

import com.kgplatform.business.kinder.client.dto.TenantDataSourceConfigDto;

/**
 * 租户数据源配置 Client
 */
public interface TenantDataSourceConfigClient {

    TenantDataSourceConfigDto getConfigByTenantId(Long tenantId);
}
