package com.kgplatform.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.auth.domain.dto.CurrentUserDto;
import com.kgplatform.auth.domain.dto.LoginDto;
import com.kgplatform.auth.domain.po.AuthUser;
import com.kgplatform.auth.domain.vo.LoginVo;
import com.kgplatform.auth.domain.vo.SwitchTenantVo;
import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.system.domain.dto.CurrentUserTenantDto;

import java.util.List;

/**
 * 认证服务接口
 */
public interface AuthService extends IService<AuthUser> {

    LoginDto login(LoginVo vo);

    CurrentUserDto currentUser(LoginUser loginUser);

    List<CurrentUserTenantDto> tenants(LoginUser loginUser);

    LoginDto switchTenant(LoginUser loginUser, SwitchTenantVo vo);
}
