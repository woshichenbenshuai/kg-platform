package com.kgplatform.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.auth.domain.dto.CurrentUserDto;
import com.kgplatform.auth.domain.dto.LoginDto;
import com.kgplatform.auth.domain.po.AuthUser;
import com.kgplatform.auth.domain.vo.LoginVo;
import com.kgplatform.auth.domain.vo.SwitchTenantVo;
import com.kgplatform.auth.mapper.AuthUserMapper;
import com.kgplatform.auth.service.AuthService;
import com.kgplatform.common.security.jwt.JwtUtils;
import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.dto.CurrentUserAccessDto;
import com.kgplatform.system.domain.dto.CurrentUserTenantDto;
import com.kgplatform.system.service.ICurrentUserAccessService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 认证服务实现
 */
@Service
public class AuthServiceImpl extends ServiceImpl<AuthUserMapper, AuthUser> implements AuthService {

    private final AuthUserMapper authUserMapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final ICurrentUserAccessService currentUserAccessService;

    public AuthServiceImpl(AuthUserMapper authUserMapper,
                           JwtUtils jwtUtils,
                           PasswordEncoder passwordEncoder,
                           ICurrentUserAccessService currentUserAccessService) {
        this.authUserMapper = authUserMapper;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.currentUserAccessService = currentUserAccessService;
    }

    @Override
    public LoginDto login(LoginVo vo) {
        Asserts.notNull(vo, "登录参数不能为空");
        Asserts.notBlank(vo.getUsername(), "用户名不能为空");
        Asserts.notBlank(vo.getPassword(), "密码不能为空");

        AuthUser authUser = authUserMapper.selectByUsername(vo.getUsername());
        boolean invalid = authUser == null
                || authUser.getDeleteStatus() == null
                || authUser.getDeleteStatus()
                || authUser.getStatus() == null
                || authUser.getStatus() != 1
                || authUser.getPassword() == null
                || authUser.getPassword().isBlank()
                || !passwordEncoder.matches(vo.getPassword(), authUser.getPassword());
        Asserts.isTrue(!invalid, "用户名或密码错误");

        Long tenantId = currentUserAccessService.getCurrentTenantId(authUser.getId());
        return new LoginDto(jwtUtils.createToken(authUser.getId(), authUser.getUsername(), authUser.getNickname(), tenantId));
    }

    @Override
    public CurrentUserDto currentUser(LoginUser loginUser) {
        Asserts.notNull(loginUser, "当前登录用户不能为空");
        Asserts.notNull(loginUser.getUserId(), "当前登录用户主键不能为空");
        Asserts.notBlank(loginUser.getUsername(), "当前登录用户名不能为空");

        CurrentUserAccessDto accessDto = currentUserAccessService.getCurrentUserAccess(loginUser.getUserId());
        CurrentUserDto currentUserDto = new CurrentUserDto();
        currentUserDto.setUserId(loginUser.getUserId());
        currentUserDto.setUsername(loginUser.getUsername());
        currentUserDto.setNickname(loginUser.getNickname() != null ? loginUser.getNickname() : accessDto.getNickname());
        currentUserDto.setTenantId(loginUser.getTenantId() != null ? loginUser.getTenantId() : accessDto.getTenantId());
        currentUserDto.setRoleCodes(accessDto.getRoleCodes());
        currentUserDto.setRoleNames(accessDto.getRoleNames());
        currentUserDto.setMenus(accessDto.getMenus());
        currentUserDto.setTenants(accessDto.getTenants());
        return currentUserDto;
    }

    @Override
    public List<CurrentUserTenantDto> tenants(LoginUser loginUser) {
        Asserts.notNull(loginUser, "Current login user is required");
        Asserts.notNull(loginUser.getUserId(), "Current login user id is required");
        return currentUserAccessService.getAccessibleTenants(loginUser.getUserId());
    }

    @Override
    public LoginDto switchTenant(LoginUser loginUser, SwitchTenantVo vo) {
        Asserts.notNull(loginUser, "Current login user is required");
        Asserts.notNull(loginUser.getUserId(), "Current login user id is required");
        Asserts.notNull(vo, "Switch tenant parameter is required");
        Asserts.notNull(vo.getTenantId(), "Tenant id is required");
        currentUserAccessService.assertTenantAccessible(loginUser.getUserId(), vo.getTenantId());
        return new LoginDto(jwtUtils.createToken(
                loginUser.getUserId(),
                loginUser.getUsername(),
                loginUser.getNickname(),
                vo.getTenantId()
        ));
    }
}
