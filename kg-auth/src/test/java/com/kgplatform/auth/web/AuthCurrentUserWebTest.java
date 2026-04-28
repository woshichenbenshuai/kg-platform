package com.kgplatform.auth.web;

import com.kgplatform.auth.domain.dto.CurrentUserDto;
import com.kgplatform.auth.service.AuthService;
import com.kgplatform.auth.web.rest.front.AuthResource;
import com.kgplatform.common.datasource.context.TenantContextHolder;
import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.security.filter.JwtAuthenticationFilter;
import com.kgplatform.common.security.jwt.JwtUtils;
import com.kgplatform.common.security.resolver.JwtLoginUserResolver;
import com.kgplatform.common.web.exception.GlobalExceptionHandler;
import com.kgplatform.system.service.ICurrentUserAccessService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthCurrentUserWebTest {

    private static final String SECRET = "kg-platform-test-secret-1234567890";

    private MockMvc mockMvc;
    private AuthService authService;
    private ICurrentUserAccessService currentUserAccessService;

    @BeforeEach
    void setUp() {
        authService = Mockito.mock(AuthService.class);
        currentUserAccessService = Mockito.mock(ICurrentUserAccessService.class);
        JwtLoginUserResolver jwtLoginUserResolver = new JwtLoginUserResolver(new JwtUtils(SECRET, 3600));
        AuthCurrentUserInterceptor interceptor = new AuthCurrentUserInterceptor(currentUserAccessService);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
                jwtLoginUserResolver,
                new com.kgplatform.common.security.config.JsonAuthenticationEntryPoint()
        );
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthResource(authService))
                .addFilters(filter)
                .addInterceptors(interceptor)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
        LoginUserContextHolder.clear();
    }

    @Test
    void should_return_401_json_when_current_user_missing_authorization() throws Exception {
        mockMvc.perform(get("/auth/current-user"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("401"));
    }

    @Test
    void should_delegate_current_user_query_when_token_valid() throws Exception {
        CurrentUserDto dto = new CurrentUserDto();
        dto.setUserId(100L);
        dto.setUsername("tester");
        dto.setTenantId(1L);

        when(currentUserAccessService.getCurrentTenantId(anyLong())).thenReturn(1L);
        when(authService.currentUser(eq(100L), eq("tester"))).thenReturn(dto);

        mockMvc.perform(get("/auth/current-user").header("Authorization", "Bearer " + createValidToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data.userId").value(100))
                .andExpect(jsonPath("$.data.username").value("tester"))
                .andExpect(jsonPath("$.data.tenantId").value(1));

        verify(currentUserAccessService).getCurrentTenantId(100L);
        verify(authService).currentUser(100L, "tester");
    }

    private String createValidToken() {
        return new JwtUtils(SECRET, 3600).createToken(100L, "tester");
    }
}
