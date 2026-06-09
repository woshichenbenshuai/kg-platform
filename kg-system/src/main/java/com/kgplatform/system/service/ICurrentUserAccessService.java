package com.kgplatform.system.service;

import com.kgplatform.system.domain.dto.CurrentUserAccessDto;
import com.kgplatform.system.domain.dto.CurrentUserTenantDto;

import java.util.List;

/**
 * 当前用户访问聚合 Service 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
 */
public interface ICurrentUserAccessService {

    /**
     * 查询当前用户访问聚合
     *
     * @param userId 用户主键
     * @return 当前用户访问聚合
     */
    CurrentUserAccessDto getCurrentUserAccess(Long userId);

    /**
     * 查询当前用户默认租户主键
     *
     * @param userId 用户主键
     * @return 当前租户主键
     */
    Long getCurrentTenantId(Long userId);

    List<CurrentUserTenantDto> getAccessibleTenants(Long userId);

    void assertTenantAccessible(Long userId, Long tenantId);
}
