package com.kgplatform.business.kinder.service.impl;

import com.kgplatform.business.kinder.client.TenantQueryClient;
import com.kgplatform.business.kinder.client.UserQueryClient;
import com.kgplatform.business.kinder.domain.dto.TenantDto;
import com.kgplatform.business.kinder.service.TenantQueryService;
import com.kgplatform.common.datasource.context.TenantContextHolder;
import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Service;

/**
 * 租户查询服务实现
 */
@Service
public class TenantQueryServiceImpl implements TenantQueryService {

    private final TenantQueryClient tenantQueryClient;
    private final UserQueryClient userQueryClient;

    public TenantQueryServiceImpl(TenantQueryClient tenantQueryClient, UserQueryClient userQueryClient) {
        this.tenantQueryClient = tenantQueryClient;
        this.userQueryClient = userQueryClient;
    }

    @Override
    public TenantDto getCurrentTenant() {
        Long tenantId = TenantContextHolder.getTenantId();
        Asserts.notNull(tenantId, "当前租户主键不能为空");

        TenantDto tenant = tenantQueryClient.getTenantById(tenantId);
        Asserts.notNull(tenant, "当前租户不存在");
        return tenant;
    }

    @Override
    public String getCurrentUserNickname() {
        Long userId = LoginUserContextHolder.require().getUserId();
        String nickname = userQueryClient.getNicknameByUserId(userId);
        Asserts.notBlank(nickname, "当前登录用户昵称不存在");
        return nickname;
    }
}
