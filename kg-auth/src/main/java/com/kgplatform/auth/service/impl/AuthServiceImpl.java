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
        Asserts.notNull(vo, "Login payload can not be null");
        Asserts.notBlank(vo.getUsername(), "Username can not be blank");
        Asserts.notBlank(vo.getPassword(), "Password can not be blank");

        AuthUser authUser = authUserMapper.selectByUsername(vo.getUsername());
        boolean invalid = authUser == null
                || authUser.getDeleteStatus() == null
                || authUser.getDeleteStatus()
                || authUser.getStatus() == null
                || authUser.getStatus() != 1
                || authUser.getPassword() == null
                || authUser.getPassword().isBlank()
                || !passwordEncoder.matches(vo.getPassword(), authUser.getPassword());
        Asserts.isTrue(!invalid, "Username or password is invalid");

        return new LoginDto(jwtUtils.createToken(authUser.getId(), authUser.getUsername()));
    }

    @Override
    public CurrentUserDto currentUser(Long currentUserId, String currentUsername) {
        Asserts.notNull(currentUserId, "Current user id can not be null");
        Asserts.notBlank(currentUsername, "Current username can not be blank");

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
