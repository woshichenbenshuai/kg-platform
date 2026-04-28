package com.kgplatform.system.web.rest;

import com.kgplatform.common.security.filter.JwtAuthenticationFilter;
import com.kgplatform.common.security.jwt.JwtUtils;
import com.kgplatform.common.security.resolver.JwtLoginUserResolver;
import com.kgplatform.common.web.exception.GlobalExceptionHandler;
import com.kgplatform.system.domain.po.TenantDbConfig;
import com.kgplatform.system.service.ICurrentUserAccessService;
import com.kgplatform.system.service.ITenantDbConfigService;
import com.kgplatform.system.web.SystemAuthInterceptor;
import com.kgplatform.system.web.TenantScopeHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TenantDbConfigResourceScopeWebTest {

    private static final String SECRET = "kg-platform-test-secret-1234567890";

    private MockMvc mockMvc;
    private ITenantDbConfigService tenantDbConfigService;
    private ICurrentUserAccessService currentUserAccessService;

    @BeforeEach
    void setUp() {
        tenantDbConfigService = Mockito.mock(ITenantDbConfigService.class);
        currentUserAccessService = Mockito.mock(ICurrentUserAccessService.class);

        JwtLoginUserResolver jwtLoginUserResolver = new JwtLoginUserResolver(new JwtUtils(SECRET, 3600));
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
                jwtLoginUserResolver,
                new com.kgplatform.common.security.config.JsonAuthenticationEntryPoint()
        );
        SystemAuthInterceptor interceptor = new SystemAuthInterceptor(currentUserAccessService);

        mockMvc = MockMvcBuilders.standaloneSetup(new TenantDbConfigResource(tenantDbConfigService, new TenantScopeHelper()))
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
    void schemaVersion_should_use_current_tenant_when_request_param_missing() throws Exception {
        when(currentUserAccessService.getCurrentTenantId(anyLong())).thenReturn(1L);
        when(tenantDbConfigService.getSchemaVersion(1L)).thenReturn("v1");

        mockMvc.perform(get("/tenant-db-configs/schema-version")
                        .header("Authorization", "Bearer " + createValidToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data").value("v1"));

        verify(tenantDbConfigService).getSchemaVersion(1L);
    }

    @Test
    void selectOne_should_reject_cross_tenant_access() throws Exception {
        TenantDbConfig entity = new TenantDbConfig();
        entity.setId(10L);
        entity.setTenantId(2L);

        when(currentUserAccessService.getCurrentTenantId(anyLong())).thenReturn(1L);
        when(tenantDbConfigService.getById(10L)).thenReturn(entity);

        mockMvc.perform(get("/tenant-db-configs")
                        .param("id", "10")
                        .header("Authorization", "Bearer " + createValidToken()))
                .andExpect(status().isForbidden())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("403"));
    }

    private String createValidToken() {
        return new JwtUtils(SECRET, 3600).createToken(100L, "tester");
    }
}
