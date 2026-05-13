package com.kgplatform.system.web;

import com.kgplatform.common.datasource.context.TenantContextHolder;
import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.common.web.core.Status;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.dto.CurrentUserAccessDto;
import com.kgplatform.system.service.ICurrentUserAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 当前租户范围辅助类
 */
@Component
public class TenantScopeHelper {

    private static final String PLATFORM_ADMIN = "PLATFORM_ADMIN";

    private final ICurrentUserAccessService currentUserAccessService;

    public TenantScopeHelper() {
        this.currentUserAccessService = null;
    }

    @Autowired
    public TenantScopeHelper(ICurrentUserAccessService currentUserAccessService) {
        this.currentUserAccessService = currentUserAccessService;
    }

    public Long currentTenantId() {
        Long tenantId = TenantContextHolder.getTenantId();
        Asserts.notNull(tenantId, "当前请求未绑定租户");
        return tenantId;
    }

    public Long resolveTenantId(Long requestTenantId) {
        if (requestTenantId != null) {
            return requestTenantId;
        }
        if (isPlatformAdmin()) {
            return null;
        }
        return currentTenantId();
    }

    public void assertAccessible(Long resourceTenantId) {
        Asserts.notNull(resourceTenantId, "资源未绑定租户");
        Asserts.isTrue(isPlatformAdmin() || Objects.equals(currentTenantId(), resourceTenantId), Status.FORBIDDEN);
    }

    public boolean isPlatformAdmin() {
        if (currentUserAccessService == null) {
            return false;
        }
        LoginUser loginUser = LoginUserContextHolder.get();
        if (loginUser == null || loginUser.getUserId() == null) {
            return false;
        }
        CurrentUserAccessDto access = currentUserAccessService.getCurrentUserAccess(loginUser.getUserId());
        List<String> roleCodes = access.getRoleCodes();
        return roleCodes != null && roleCodes.contains(PLATFORM_ADMIN);
    }
}
