package com.kgplatform.common.datasource.context;

/**
 * TenantContextHolder
 * <p>
 * TenantContextHolder业务类
 *
 * @author kg_chen
 * @since 2026-04-22 18:50:54
 */
public final class TenantContextHolder {

    private static final ThreadLocal<Long> TENANT_HOLDER = new ThreadLocal<>();

    private TenantContextHolder() {
    }

    public static void setTenantId(Long tenantId) {
        TENANT_HOLDER.set(tenantId);
    }

    public static Long getTenantId() {
        return TENANT_HOLDER.get();
    }

    public static void clear() {
        TENANT_HOLDER.remove();
    }
}
