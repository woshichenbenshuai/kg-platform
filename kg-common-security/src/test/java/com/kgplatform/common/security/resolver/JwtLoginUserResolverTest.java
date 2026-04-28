package com.kgplatform.common.security.resolver;

import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.security.jwt.JwtUtils;
import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.common.web.exception.ApiException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtLoginUserResolverTest {

    private static final String SECRET = "kg-platform-test-secret-1234567890";

    private final JwtLoginUserResolver jwtLoginUserResolver = new JwtLoginUserResolver(new JwtUtils(SECRET, 3600));

    @AfterEach
    void tearDown() {
        jwtLoginUserResolver.clearContext();
    }

    @Test
    void resolveAndBind_should_store_login_user_in_context() {
        String token = new JwtUtils(SECRET, 3600).createToken(100L, "tester");

        LoginUser loginUser = jwtLoginUserResolver.resolveAndBind("Bearer " + token);

        assertNotNull(loginUser);
        assertEquals(100L, loginUser.getUserId());
        assertEquals("tester", loginUser.getUsername());
        assertEquals(loginUser.getUserId(), LoginUserContextHolder.require().getUserId());
    }

    @Test
    void resolve_should_reject_missing_authorization() {
        ApiException exception = assertThrows(ApiException.class, () -> jwtLoginUserResolver.resolve(null));

        assertEquals("401", exception.getCode());
        assertNull(LoginUserContextHolder.get());
    }
}
