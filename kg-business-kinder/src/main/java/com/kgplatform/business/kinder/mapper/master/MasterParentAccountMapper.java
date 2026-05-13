package com.kgplatform.business.kinder.mapper.master;

import org.apache.ibatis.annotations.Param;

/**
 * 主库家长账号 Mapper
 */
public interface MasterParentAccountMapper {

    Long selectUserIdByUsername(@Param("username") String username);

    int insertUser(@Param("id") Long id,
                   @Param("username") String username,
                   @Param("nickname") String nickname,
                   @Param("phone") String phone,
                   @Param("password") String password);

    Long selectParentRoleId();

    Long selectUserTenantId(@Param("userId") Long userId,
                            @Param("tenantId") Long tenantId);

    int insertUserTenant(@Param("id") Long id,
                         @Param("userId") Long userId,
                         @Param("tenantId") Long tenantId);

    Long selectUserRoleId(@Param("userId") Long userId,
                          @Param("roleId") Long roleId);

    int insertUserRole(@Param("id") Long id,
                       @Param("userId") Long userId,
                       @Param("roleId") Long roleId);
}
