package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.dto.TenantOperatorAccountDto;
import com.kgplatform.system.domain.po.Role;
import com.kgplatform.system.domain.po.Tenant;
import com.kgplatform.system.domain.po.User;
import com.kgplatform.system.domain.po.UserRole;
import com.kgplatform.system.domain.po.UserTenant;
import com.kgplatform.system.domain.vo.TenantOperatorAccountVo;
import com.kgplatform.system.service.IRoleService;
import com.kgplatform.system.service.ITenantService;
import com.kgplatform.system.service.IUserRoleService;
import com.kgplatform.system.service.IUserService;
import com.kgplatform.system.service.IUserTenantService;
import com.kgplatform.system.service.TenantOperatorAccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tenant operator account provisioning service implementation.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TenantOperatorAccountServiceImpl implements TenantOperatorAccountService {

    private static final String OPERATOR_ROLE_CODE = "OPERATOR";
    private static final String OPERATOR_IDENTITY_TYPE = "OPERATOR";

    private final ITenantService tenantService;
    private final IUserService userService;
    private final IUserTenantService userTenantService;
    private final IRoleService roleService;
    private final IUserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    public TenantOperatorAccountServiceImpl(ITenantService tenantService,
                                            IUserService userService,
                                            IUserTenantService userTenantService,
                                            IRoleService roleService,
                                            IUserRoleService userRoleService,
                                            PasswordEncoder passwordEncoder) {
        this.tenantService = tenantService;
        this.userService = userService;
        this.userTenantService = userTenantService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public TenantOperatorAccountDto openAccount(Long tenantId, TenantOperatorAccountVo vo) {
        Asserts.notNull(tenantId, "Tenant id is required");
        Asserts.notNull(vo, "Operator account parameter is required");
        Asserts.notBlank(vo.getPhone(), "Phone is required");
        Asserts.notBlank(vo.getNickname(), "Operator name is required");
        Asserts.notBlank(vo.getPassword(), "Password is required");

        Tenant tenant = tenantService.getById(tenantId);
        Asserts.isTrue(tenant != null && !Boolean.TRUE.equals(tenant.getDeleteStatus()), "Tenant does not exist");

        User user = ensureUser(vo);
        ensureUserTenant(user.getId(), tenantId);
        ensureOperatorRole(user.getId());

        return new TenantOperatorAccountDto()
                .setUserId(user.getId())
                .setTenantId(tenantId)
                .setUsername(user.getUsername());
    }

    private User ensureUser(TenantOperatorAccountVo vo) {
        User existing = userService.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, vo.getPhone())
                .eq(User::getDeleteStatus, Boolean.FALSE)
                .last("LIMIT 1"));
        if (existing != null) {
            return existing;
        }

        User user = new User()
                .setUsername(vo.getPhone())
                .setNickname(vo.getNickname())
                .setPhone(vo.getPhone())
                .setPassword(passwordEncoder.encode(vo.getPassword()))
                .setStatus(1);
        user.setDeleteStatus(Boolean.FALSE);
        userService.save(user);
        return user;
    }

    private void ensureUserTenant(Long userId, Long tenantId) {
        UserTenant existing = userTenantService.getOne(Wrappers.<UserTenant>lambdaQuery()
                .eq(UserTenant::getUserId, userId)
                .eq(UserTenant::getTenantId, tenantId)
                .eq(UserTenant::getIdentityType, OPERATOR_IDENTITY_TYPE)
                .eq(UserTenant::getDeleteStatus, Boolean.FALSE)
                .last("LIMIT 1"));
        if (existing != null) {
            return;
        }

        boolean hasDefaultTenant = userTenantService.count(Wrappers.<UserTenant>lambdaQuery()
                .eq(UserTenant::getUserId, userId)
                .eq(UserTenant::getDefaultFlag, Boolean.TRUE)
                .eq(UserTenant::getDeleteStatus, Boolean.FALSE)) > 0;

        UserTenant userTenant = new UserTenant()
                .setUserId(userId)
                .setTenantId(tenantId)
                .setIdentityType(OPERATOR_IDENTITY_TYPE)
                .setDefaultFlag(!hasDefaultTenant)
                .setStatus(Boolean.TRUE);
        userTenant.setDeleteStatus(Boolean.FALSE);
        userTenantService.save(userTenant);
    }

    private void ensureOperatorRole(Long userId) {
        Role role = roleService.getOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleCode, OPERATOR_ROLE_CODE)
                .eq(Role::getDeleteStatus, Boolean.FALSE)
                .eq(Role::getStatus, Boolean.TRUE)
                .last("LIMIT 1"));
        Asserts.notNull(role, "Operator role OPERATOR does not exist");

        UserRole existing = userRoleService.getOne(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, role.getId())
                .eq(UserRole::getDeleteStatus, Boolean.FALSE)
                .last("LIMIT 1"));
        if (existing != null) {
            return;
        }

        UserRole userRole = new UserRole()
                .setUserId(userId)
                .setRoleId(role.getId())
                .setStatus(Boolean.TRUE);
        userRole.setDeleteStatus(Boolean.FALSE);
        userRoleService.save(userRole);
    }
}
