package com.kgplatform.system.web;

import com.kgplatform.common.security.filter.JwtAuthenticationFilter;
import com.kgplatform.common.security.jwt.JwtUtils;
import com.kgplatform.common.security.resolver.JwtLoginUserResolver;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.GlobalExceptionHandler;
import com.kgplatform.system.service.ICurrentUserAccessService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SystemAuthInterceptorWebTest {

    private static final String SECRET = "kg-platform-test-secret-1234567890";

    private MockMvc mockMvc;
    private ICurrentUserAccessService currentUserAccessService;

    @BeforeEach
    void setUp() {
        currentUserAccessService = Mockito.mock(ICurrentUserAccessService.class);
        JwtLoginUserResolver jwtLoginUserResolver = new JwtLoginUserResolver(new JwtUtils(SECRET, 3600));
        SystemAuthInterceptor interceptor = new SystemAuthInterceptor(currentUserAccessService);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
                jwtLoginUserResolver,
                new com.kgplatform.common.security.config.JsonAuthenticationEntryPoint()
        );
        mockMvc = MockMvcBuilders.standaloneSetup(new TestSystemController())
                .addFilters(filter)
                .addInterceptors(interceptor)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @AfterEach
    void tearDown() {
        com.kgplatform.common.datasource.context.TenantContextHolder.clear();
        com.kgplatform.common.security.context.LoginUserContextHolder.clear();
    }

    @Test
    void should_return_401_json_when_authorization_missing() throws Exception {
        mockMvc.perform(get("/users/pages"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("401"));
    }

    @Test
    void should_return_401_json_when_token_invalid() throws Exception {
        mockMvc.perform(get("/users/pages").header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("401"));
    }

    @Test
    void should_allow_request_when_token_valid() throws Exception {
        when(currentUserAccessService.getCurrentTenantId(anyLong())).thenReturn(1L);

        mockMvc.perform(get("/users/pages").header("Authorization", "Bearer " + createValidToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data").value("ok"));

        verify(currentUserAccessService).getCurrentTenantId(100L);
    }

    private String createValidToken() {
        return new JwtUtils(SECRET, 3600).createToken(100L, "tester");
    }

    @RestController
    static class TestSystemController {

        @GetMapping("/users/pages")
        public Result<String> pages() {
            return Result.ok("ok");
        }
    }
}
