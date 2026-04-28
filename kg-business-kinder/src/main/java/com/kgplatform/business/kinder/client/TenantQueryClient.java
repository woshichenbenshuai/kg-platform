package com.kgplatform.business.kinder.client;

import com.kgplatform.business.kinder.domain.dto.TenantDto;

/**
 * 租户查询 Client
 */
public interface TenantQueryClient {

    TenantDto getTenantById(Long tenantId);

    Long getDefaultTenantIdByUserId(Long userId);
}
