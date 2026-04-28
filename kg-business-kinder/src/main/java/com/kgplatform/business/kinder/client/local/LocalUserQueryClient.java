package com.kgplatform.business.kinder.client.local;

import com.kgplatform.business.kinder.client.UserQueryClient;
import com.kgplatform.business.kinder.mapper.master.MasterUserMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 本地用户查询 Client
 */
@Component
@ConditionalOnProperty(prefix = "kg.system-client", name = "mode", havingValue = "local", matchIfMissing = true)
public class LocalUserQueryClient implements UserQueryClient {

    private final MasterUserMapper masterUserMapper;

    public LocalUserQueryClient(MasterUserMapper masterUserMapper) {
        this.masterUserMapper = masterUserMapper;
    }

    @Override
    public String getNicknameByUserId(Long userId) {
        return masterUserMapper.selectEnabledNicknameByUserId(userId);
    }
}
