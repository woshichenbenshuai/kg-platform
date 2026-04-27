package com.kgplatform.system.util;

import com.kgplatform.common.core.constant.SecurityConstants;
import com.kgplatform.common.security.jwt.JwtUtils;
import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.common.web.core.Status;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Component;

/**
 * 登录用户解析
 * <p>
 * SystemLoginUserResolver工具类
 *
 * @author kg_chen
 * @since 2026-04-23 08:59:19
 */
@Component
public class SystemLoginUserResolver {

    private final JwtUtils jwtUtils;

    public SystemLoginUserResolver(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public LoginUser resolve(String authorization) {
        if (authorization == null || !authorization.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            Asserts.fail(Status.UNAUTHORIZED);

        }

        String token = authorization.substring(SecurityConstants.TOKEN_PREFIX.length());
        if (token.isBlank()) {
            Asserts.fail(Status.UNAUTHORIZED);

        }
        return jwtUtils.parseToken(token);

    }
}
