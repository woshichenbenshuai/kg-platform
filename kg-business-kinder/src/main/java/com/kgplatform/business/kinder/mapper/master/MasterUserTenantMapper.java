package com.kgplatform.business.kinder.mapper.master;

import org.apache.ibatis.annotations.Param;

/**
 * 主库用户租户 Mapper
 */
public interface MasterUserTenantMapper {

    Long selectDefaultTenantIdByUserId(@Param("userId") Long userId);
}
