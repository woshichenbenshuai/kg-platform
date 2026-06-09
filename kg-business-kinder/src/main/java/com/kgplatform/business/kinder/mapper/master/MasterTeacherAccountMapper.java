package com.kgplatform.business.kinder.mapper.master;

import org.apache.ibatis.annotations.Param;

/**
 * Master database mapper for teacher account provisioning.
 */
public interface MasterTeacherAccountMapper {

    Long selectUserIdByUsername(@Param("username") String username);

    int insertUser(@Param("id") Long id,
                   @Param("username") String username,
                   @Param("nickname") String nickname,
                   @Param("phone") String phone,
                   @Param("password") String password);

    Long selectTeacherRoleId();

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
