package com.kgplatform.common.security.filter;

import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.common.security.resolver.JwtLoginUserResolver;
import com.kgplatform.common.web.exception.ApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT 认证过滤器
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtLoginUserResolver jwtLoginUserResolver;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final List<RequestMatcher> whitelistMatchers = List.of(
            new AntPathRequestMatcher("/auth/login"),
            new AntPathRequestMatcher("/actuator/**"),
            new AntPathRequestMatcher("/error"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/swagger-ui.html")
    );

    public JwtAuthenticationFilter(JwtLoginUserResolver jwtLoginUserResolver,
                                   AuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtLoginUserResolver = jwtLoginUserResolver;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return whitelistMatchers.stream().anyMatch(matcher -> matcher.matches(request));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                LoginUser loginUser = jwtLoginUserResolver.resolveAndBind(request.getHeader("Authorization"));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        loginUser,
                        null,
                        List.of()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (ApiException ex) {
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(
                    request,
                    response,
                    new InsufficientAuthenticationException(ex.getMessage(), ex)
            );
        } finally {
            SecurityContextHolder.clearContext();
            jwtLoginUserResolver.clearContext();
            LoginUserContextHolder.clear();
        }
    }
}
