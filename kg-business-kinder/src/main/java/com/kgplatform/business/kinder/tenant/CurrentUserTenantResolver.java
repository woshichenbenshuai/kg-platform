package com.kgplatform.business.kinder.tenant;

/**
 * 当前用户租户解析器
 */
public interface CurrentUserTenantResolver {

    Long resolveTenantId(Long userId);
}
