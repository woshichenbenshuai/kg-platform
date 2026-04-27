package com.kgplatform.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.auth.domain.dto.CurrentUserDto;
import com.kgplatform.auth.domain.dto.LoginDto;
import com.kgplatform.auth.domain.po.AuthUser;
import com.kgplatform.auth.domain.vo.LoginVo;
import com.kgplatform.auth.mapper.AuthUserMapper;
import com.kgplatform.auth.service.AuthService;
import com.kgplatform.common.security.jwt.JwtUtils;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.dto.CurrentUserAccessDto;
import com.kgplatform.system.service.ICurrentUserAccessService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现类
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
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

        return new LoginDto(jwtUtils.createToken(authUser.getId(), authUser.getUsername()));
    }

    @Override
    public CurrentUserDto currentUser(Long currentUserId, String currentUsername) {
        Asserts.notNull(currentUserId, "当前登录用户主键不能为空");
        Asserts.notBlank(currentUsername, "当前登录用户名不能为空");

        CurrentUserAccessDto accessDto = currentUserAccessService.getCurrentUserAccess(currentUserId);
        CurrentUserDto currentUserDto = new CurrentUserDto();
        currentUserDto.setUserId(currentUserId);
        currentUserDto.setUsername(currentUsername);
        currentUserDto.setTenantId(accessDto.getTenantId());
        currentUserDto.setRoleCodes(accessDto.getRoleCodes());
        currentUserDto.setRoleNames(accessDto.getRoleNames());
        currentUserDto.setMenus(accessDto.getMenus());
        return currentUserDto;
    }
}
