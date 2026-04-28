package com.kgplatform.auth.web.rest.front;

import com.kgplatform.auth.domain.dto.CurrentUserDto;
import com.kgplatform.auth.domain.dto.LoginDto;
import com.kgplatform.auth.domain.vo.LoginVo;
import com.kgplatform.auth.service.AuthService;
import com.kgplatform.common.core.constant.SecurityConstants;
import com.kgplatform.common.security.jwt.JwtUtils;
import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 */
@Validated
@RestController
@Tag(name = "AuthResource", description = "认证相关接口")
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthResource {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    public AuthResource(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<LoginDto> login(@RequestBody LoginVo vo) {
        return Result.ok(authService.login(vo));
    }

    @GetMapping("/current-user")
    @Operation(summary = "查询当前登录用户信息")
    public Result<CurrentUserDto> currentUser(
            @Parameter(description = "Bearer Token", required = true)
            @RequestHeader(SecurityConstants.AUTHORIZATION_HEADER) String authorization) {
        Asserts.notNull(authorization, "Authorization请求头不能为空");
        Asserts.isTrue(authorization.startsWith(SecurityConstants.TOKEN_PREFIX), "Authorization请求头不合法");

        String token = authorization.substring(SecurityConstants.TOKEN_PREFIX.length());
        try {
            LoginUser loginUser = jwtUtils.parseToken(token);
            return Result.ok(authService.currentUser(loginUser.getUserId(), loginUser.getUsername()));
        } catch (Exception ex) {
            Asserts.fail("登录状态无效，请重新登录");
            return null;
        }
    }
}
