package com.kgplatform.system.web;

import com.kgplatform.common.core.constant.SecurityConstants;
import com.kgplatform.common.datasource.context.TenantContextHolder;
import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.system.service.ICurrentUserAccessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 系统接口鉴权拦截器
 */
@Component
public class SystemAuthInterceptor implements HandlerInterceptor {

    private final ICurrentUserAccessService currentUserAccessService;

    public SystemAuthInterceptor(ICurrentUserAccessService currentUserAccessService) {
        this.currentUserAccessService = currentUserAccessService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        if (request.getHeader(SecurityConstants.AUTHORIZATION_HEADER) == null) {
            return true;
        }

        LoginUser loginUser = LoginUserContextHolder.require();
        Long tenantId = loginUser.getTenantId() != null
                ? loginUser.getTenantId()
                : currentUserAccessService.getCurrentTenantId(loginUser.getUserId());
        TenantContextHolder.setTenantId(tenantId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContextHolder.clear();
    }
}
