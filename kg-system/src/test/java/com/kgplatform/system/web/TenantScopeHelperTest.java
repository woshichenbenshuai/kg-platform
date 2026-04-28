package com.kgplatform.system.web;

import com.kgplatform.common.datasource.context.TenantContextHolder;
import com.kgplatform.common.web.exception.ApiException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TenantScopeHelperTest {

    private final TenantScopeHelper tenantScopeHelper = new TenantScopeHelper();

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void resolveTenantId_should_fallback_to_context() {
        TenantContextHolder.setTenantId(9L);

        assertEquals(9L, tenantScopeHelper.resolveTenantId(null));
    }

    @Test
    void assertAccessible_should_reject_cross_tenant_access() {
        TenantContextHolder.setTenantId(9L);

        ApiException exception = assertThrows(ApiException.class, () -> tenantScopeHelper.assertAccessible(10L));
        assertEquals("403", exception.getCode());
    }
}
