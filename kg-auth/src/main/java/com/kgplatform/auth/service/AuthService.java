package com.kgplatform.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.auth.domain.dto.CurrentUserDto;
import com.kgplatform.auth.domain.dto.LoginDto;
import com.kgplatform.auth.domain.po.AuthUser;
import com.kgplatform.auth.domain.vo.LoginVo;

/**
 * 认证服务接口
 */
public interface AuthService extends IService<AuthUser> {

    LoginDto login(LoginVo vo);

    CurrentUserDto currentUser(Long currentUserId, String currentUsername);
}
