package com.kgplatform.business.kinder.web;

import com.kgplatform.business.kinder.tenant.CurrentUserTenantResolver;
import com.kgplatform.common.datasource.context.TenantContextHolder;
import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.security.model.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 园所业务租户上下文拦截器
 */
@Component
public class BusinessTenantInterceptor implements HandlerInterceptor {

    private final CurrentUserTenantResolver currentUserTenantResolver;

    public BusinessTenantInterceptor(CurrentUserTenantResolver currentUserTenantResolver) {
        this.currentUserTenantResolver = currentUserTenantResolver;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        LoginUser loginUser = LoginUserContextHolder.require();
        Long tenantId = loginUser.getTenantId() != null
                ? loginUser.getTenantId()
                : currentUserTenantResolver.resolveTenantId(loginUser.getUserId());
        TenantContextHolder.setTenantId(tenantId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContextHolder.clear();
    }
}
