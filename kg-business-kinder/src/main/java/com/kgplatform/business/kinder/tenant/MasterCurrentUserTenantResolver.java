package com.kgplatform.business.kinder.tenant;

import com.kgplatform.business.kinder.client.TenantQueryClient;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Component;

/**
 * 基于 Client 解析当前用户默认租户
 */
@Component
public class MasterCurrentUserTenantResolver implements CurrentUserTenantResolver {

    private final TenantQueryClient tenantQueryClient;

    public MasterCurrentUserTenantResolver(TenantQueryClient tenantQueryClient) {
        this.tenantQueryClient = tenantQueryClient;
    }

    @Override
    public Long resolveTenantId(Long userId) {
        Asserts.notNull(userId, "当前登录用户主键不能为空");
        Long tenantId = tenantQueryClient.getDefaultTenantIdByUserId(userId);
        Asserts.notNull(tenantId, "当前用户未绑定有效租户");
        return tenantId;
    }
}
