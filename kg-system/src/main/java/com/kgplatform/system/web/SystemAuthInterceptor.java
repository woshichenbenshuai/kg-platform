package com.kgplatform.system.web;

import com.kgplatform.common.core.constant.SecurityConstants;
import com.kgplatform.system.util.SystemLoginUserResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 系统接口鉴权拦截器
 * <p>
 * SystemAuthInterceptor控制层拦截器
 * @author kg_chen
 * @since 2026-04-23 08:59:19
 */
@Component
public class SystemAuthInterceptor implements HandlerInterceptor {

    private final SystemLoginUserResolver loginUserResolver;

    public SystemAuthInterceptor(SystemLoginUserResolver loginUserResolver) {
        this.loginUserResolver = loginUserResolver;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authorization = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        loginUserResolver.resolve(authorization);
        return true;
    }
}
