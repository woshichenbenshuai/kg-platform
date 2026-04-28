package com.kgplatform.business.kinder.web;

import com.kgplatform.business.kinder.tenant.CurrentUserTenantResolver;
import com.kgplatform.common.datasource.context.TenantContextHolder;
import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.security.filter.JwtAuthenticationFilter;
import com.kgplatform.common.security.jwt.JwtUtils;
import com.kgplatform.common.security.resolver.JwtLoginUserResolver;
import com.kgplatform.common.web.core.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BusinessTenantInterceptorWebTest {

    private static final String SECRET = "kg-platform-test-secret-1234567890";

    private MockMvc mockMvc;
    private CurrentUserTenantResolver currentUserTenantResolver;

    @BeforeEach
    void setUp() {
        currentUserTenantResolver = Mockito.mock(CurrentUserTenantResolver.class);
        JwtLoginUserResolver jwtLoginUserResolver = new JwtLoginUserResolver(new JwtUtils(SECRET, 3600));
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
                jwtLoginUserResolver,
                new com.kgplatform.common.security.config.JsonAuthenticationEntryPoint()
        );
        BusinessTenantInterceptor interceptor = new BusinessTenantInterceptor(currentUserTenantResolver);
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .addFilters(filter)
                .addInterceptors(interceptor)
                .build();
    }

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
        LoginUserContextHolder.clear();
    }

    @Test
    void should_bind_tenant_context_for_authenticated_request() throws Exception {
        when(currentUserTenantResolver.resolveTenantId(100L)).thenReturn(9L);

        mockMvc.perform(get("/tenant-context").header("Authorization", "Bearer " + createValidToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data").value("9"));

        verify(currentUserTenantResolver).resolveTenantId(100L);
    }

    private String createValidToken() {
        return new JwtUtils(SECRET, 3600).createToken(100L, "tester");
    }

    @RestController
    static class TestController {

        @GetMapping("/tenant-context")
        public Result<Long> currentTenant() {
            return Result.ok(TenantContextHolder.getTenantId());
        }
    }
}
