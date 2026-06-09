package com.kgplatform.auth.web.rest.front;

import com.kgplatform.auth.domain.dto.CurrentUserDto;
import com.kgplatform.auth.domain.dto.LoginDto;
import com.kgplatform.auth.domain.vo.LoginVo;
import com.kgplatform.auth.domain.vo.SwitchTenantVo;
import com.kgplatform.auth.service.AuthService;
import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.system.domain.dto.CurrentUserTenantDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 认证控制器
 */
@Validated
@RestController
@Tag(name = "AuthResource", description = "认证相关接口")
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthResource {

    private final AuthService authService;

    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<LoginDto> login(@RequestBody LoginVo vo) {
        return Result.ok(authService.login(vo));
    }

    @GetMapping("/current-user")
    @Operation(summary = "查询当前登录用户信息")
    public Result<CurrentUserDto> currentUser() {
        LoginUser loginUser = LoginUserContextHolder.require();
        return Result.ok(authService.currentUser(loginUser));
    }

    @GetMapping("/tenants")
    @Operation(summary = "查询当前用户可访问租户")
    public Result<List<CurrentUserTenantDto>> tenants() {
        LoginUser loginUser = LoginUserContextHolder.require();
        return Result.ok(authService.tenants(loginUser));
    }

    @PostMapping("/switch-tenant")
    @Operation(summary = "切换当前租户")
    public Result<LoginDto> switchTenant(@RequestBody SwitchTenantVo vo) {
        LoginUser loginUser = LoginUserContextHolder.require();
        return Result.ok(authService.switchTenant(loginUser, vo));
    }
}
