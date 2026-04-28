package com.kgplatform.business.kinder.mapper.master;

import com.kgplatform.business.kinder.domain.dto.TenantDto;
import org.apache.ibatis.annotations.Param;

/**
 * 主库租户 Mapper
 */
public interface MasterTenantMapper {

    TenantDto selectEnabledById(@Param("tenantId") Long tenantId);
}
