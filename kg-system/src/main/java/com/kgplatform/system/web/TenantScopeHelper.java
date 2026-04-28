package com.kgplatform.system.web;

import com.kgplatform.common.datasource.context.TenantContextHolder;
import com.kgplatform.common.web.core.Status;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 当前租户范围辅助类
 */
@Component
public class TenantScopeHelper {

    public Long currentTenantId() {
        Long tenantId = TenantContextHolder.getTenantId();
        Asserts.notNull(tenantId, "当前请求未绑定租户");
        return tenantId;
    }

    public Long resolveTenantId(Long requestTenantId) {
        return requestTenantId != null ? requestTenantId : currentTenantId();
    }

    public void assertAccessible(Long resourceTenantId) {
        Asserts.notNull(resourceTenantId, "资源未绑定租户");
        Asserts.isTrue(Objects.equals(currentTenantId(), resourceTenantId), Status.FORBIDDEN);
    }
}
