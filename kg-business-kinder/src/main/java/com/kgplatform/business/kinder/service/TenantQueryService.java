package com.kgplatform.business.kinder.service;

import com.kgplatform.business.kinder.domain.dto.TenantDto;

/**
 * 租户查询服务
 */
public interface TenantQueryService {

    TenantDto getCurrentTenant();

    String getCurrentUserNickname();
}
