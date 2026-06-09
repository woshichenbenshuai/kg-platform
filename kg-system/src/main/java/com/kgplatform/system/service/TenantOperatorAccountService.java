package com.kgplatform.system.service;

import com.kgplatform.system.domain.dto.TenantOperatorAccountDto;
import com.kgplatform.system.domain.vo.TenantOperatorAccountVo;

/**
 * Tenant operator account provisioning service.
 */
public interface TenantOperatorAccountService {

    TenantOperatorAccountDto openAccount(Long tenantId, TenantOperatorAccountVo vo);
}
