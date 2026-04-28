package com.kgplatform.common.security.filter;

import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.security.jwt.JwtUtils;
import com.kgplatform.common.security.resolver.JwtLoginUserResolver;
import com.kgplatform.common.web.core.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JwtAuthenticationFilterTest {

    private static final String SECRET = "kg-platform-test-secret-1234567890";

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        JwtLoginUserResolver jwtLoginUserResolver = new JwtLoginUserResolver(new JwtUtils(SECRET, 3600));
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
                jwtLoginUserResolver,
                new com.kgplatform.common.security.config.JsonAuthenticationEntryPoint()
        );
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .addFilters(filter)
                .build();
    }

    @AfterEach
    void tearDown() {
        LoginUserContextHolder.clear();
    }

    @Test
    void should_allow_whitelist_without_token() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"));
    }

    @Test
    void should_reject_protected_request_without_token() throws Exception {
        mockMvc.perform(get("/secure/ping"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("401"));
    }

    @Test
    void should_clear_context_after_successful_request() throws Exception {
        mockMvc.perform(get("/secure/ping").header("Authorization", "Bearer " + createValidToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("tester"));

        assertNull(LoginUserContextHolder.get());
    }

    private String createValidToken() {
        return new JwtUtils(SECRET, 3600).createToken(100L, "tester");
    }

    @RestController
    static class TestController {

        @GetMapping("/auth/login")
        public Result<String> login() {
            return Result.ok("login");
        }

        @GetMapping("/secure/ping")
        public Result<String> ping(HttpServletRequest request) {
            return Result.ok(LoginUserContextHolder.require().getUsername());
        }
    }
}
