package com.kgplatform.business.kinder.mapper.master;

import com.kgplatform.business.kinder.client.dto.TenantDataSourceConfigDto;
import org.apache.ibatis.annotations.Param;

/**
 * 主库租户库配置 Mapper
 */
public interface MasterTenantDbConfigMapper {

    TenantDataSourceConfigDto selectEnabledByTenantId(@Param("tenantId") Long tenantId);
}
