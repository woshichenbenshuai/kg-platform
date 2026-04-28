package com.kgplatform.business.kinder.client.remote;

import com.kgplatform.business.kinder.client.UserQueryClient;
import com.kgplatform.business.kinder.client.remote.feign.SystemUserFeignApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 远程用户查询 Client
 */
@Component
@ConditionalOnProperty(prefix = "kg.system-client", name = "mode", havingValue = "feign")
public class RemoteUserQueryClient extends RemoteClientSupport implements UserQueryClient {

    private final SystemUserFeignApi systemUserFeignApi;

    public RemoteUserQueryClient(SystemUserFeignApi systemUserFeignApi) {
        this.systemUserFeignApi = systemUserFeignApi;
    }

    @Override
    public String getNicknameByUserId(Long userId) {
        return unwrap(systemUserFeignApi.getNicknameByUserId(userId), "远程用户昵称不存在");
    }
}
