package com.kgplatform.common.security.resolver;

import com.kgplatform.common.core.constant.SecurityConstants;
import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.security.jwt.JwtUtils;
import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.common.web.core.Status;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Component;

/**
 * JWT 登录用户解析器
 */
@Component
public class JwtLoginUserResolver {

    private final JwtUtils jwtUtils;

    public JwtLoginUserResolver(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public LoginUser resolve(String authorization) {
        Asserts.notBlank(authorization, Status.UNAUTHORIZED);
        Asserts.isTrue(authorization.startsWith(SecurityConstants.TOKEN_PREFIX), Status.UNAUTHORIZED);

        String token = authorization.substring(SecurityConstants.TOKEN_PREFIX.length());
        Asserts.notBlank(token, Status.UNAUTHORIZED);

        try {
            return jwtUtils.parseToken(token);
        } catch (Exception ex) {
            Asserts.fail(Status.UNAUTHORIZED);
            return null;
        }
    }

    public LoginUser resolveAndBind(String authorization) {
        LoginUser loginUser = resolve(authorization);
        LoginUserContextHolder.set(loginUser);
        return loginUser;
    }

    public void clearContext() {
        LoginUserContextHolder.clear();
    }
}
