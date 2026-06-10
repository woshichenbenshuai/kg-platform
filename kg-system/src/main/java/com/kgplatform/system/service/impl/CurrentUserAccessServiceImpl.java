package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.MenuConverter;
import com.kgplatform.system.domain.dto.CurrentUserAccessDto;
import com.kgplatform.system.domain.dto.CurrentUserTenantDto;
import com.kgplatform.system.domain.dto.MenuDto;
import com.kgplatform.system.domain.po.Menu;
import com.kgplatform.system.domain.po.Role;
import com.kgplatform.system.domain.po.RoleMenu;
import com.kgplatform.system.domain.po.Tenant;
import com.kgplatform.system.domain.po.User;
import com.kgplatform.system.domain.po.UserRole;
import com.kgplatform.system.domain.po.UserTenant;
import com.kgplatform.system.service.ICurrentUserAccessService;
import com.kgplatform.system.service.IMenuService;
import com.kgplatform.system.service.IRoleMenuService;
import com.kgplatform.system.service.IRoleService;
import com.kgplatform.system.service.ITenantService;
import com.kgplatform.system.service.IUserRoleService;
import com.kgplatform.system.service.IUserService;
import com.kgplatform.system.service.IUserTenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 当前用户访问聚合 Service 实现类
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
 */
@Service("currentUserAccessService")
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class CurrentUserAccessServiceImpl implements ICurrentUserAccessService {

    private static final String PLATFORM_ADMIN = "PLATFORM_ADMIN";
    private static final String PLATFORM_MENU_SCOPE = "PLATFORM";

    private final IUserService userService;
    private final IUserTenantService userTenantService;
    private final IUserRoleService userRoleService;
    private final IRoleService roleService;
    private final IRoleMenuService roleMenuService;
    private final IMenuService menuService;
    private final ITenantService tenantService;

    public CurrentUserAccessServiceImpl(IUserService userService,
                                        IUserTenantService userTenantService,
                                        IUserRoleService userRoleService,
                                        IRoleService roleService,
                                        IRoleMenuService roleMenuService,
                                        IMenuService menuService,
                                        ITenantService tenantService) {
        this.userService = userService;
        this.userTenantService = userTenantService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.roleMenuService = roleMenuService;
        this.menuService = menuService;
        this.tenantService = tenantService;
    }

    @Override
    public CurrentUserAccessDto getCurrentUserAccess(Long userId) {
        Asserts.notNull(userId, "用户主键不能为空");

        User user = userService.getById(userId);
        Asserts.notNull(user, "用户不存在");
        Asserts.isTrue(Boolean.FALSE.equals(user.getDeleteStatus()), "用户不存在");
        Asserts.isTrue(Integer.valueOf(1).equals(user.getStatus()), "用户已禁用");

        List<Role> roles = getCurrentRoles(userId);
        List<String> roleCodes = roles.stream()
                .map(Role::getRoleCode)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<String> roleNames = roles.stream()
                .map(Role::getRoleName)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<MenuDto> menus = getCurrentMenus(roles);
        Long tenantId = resolveCurrentTenantId(userId, roleCodes);

        CurrentUserAccessDto dto = new CurrentUserAccessDto();
        dto.setUserId(userId);
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setTenantId(tenantId);
        dto.setRoleCodes(roleCodes);
        dto.setRoleNames(roleNames);
        dto.setMenus(menus);
        dto.setTenants(getAccessibleTenants(userId));
        return dto;
    }

    @Override
    public Long getCurrentTenantId(Long userId) {
        Asserts.notNull(userId, "用户主键不能为空");
        List<String> roleCodes = getCurrentRoles(userId).stream()
                .map(Role::getRoleCode)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        return resolveCurrentTenantId(userId, roleCodes);
    }

    @Override
    public List<CurrentUserTenantDto> getAccessibleTenants(Long userId) {
        Asserts.notNull(userId, "鐢ㄦ埛涓婚敭涓嶈兘涓虹┖");
        List<String> roleCodes = getCurrentRoles(userId).stream()
                .map(Role::getRoleCode)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (roleCodes.contains(PLATFORM_ADMIN)) {
            return Collections.emptyList();
        }

        List<UserTenant> userTenants = userTenantService.list(Wrappers.<UserTenant>lambdaQuery()
                .eq(UserTenant::getUserId, userId)
                .eq(UserTenant::getDeleteStatus, Boolean.FALSE)
                .eq(UserTenant::getStatus, Boolean.TRUE)
                .orderByDesc(UserTenant::getDefaultFlag)
                .orderByAsc(UserTenant::getId));
        if (userTenants.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> tenantIds = userTenants.stream()
                .map(UserTenant::getTenantId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (tenantIds.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, Tenant> tenantMap = tenantService.list(Wrappers.<Tenant>lambdaQuery()
                        .in(Tenant::getId, tenantIds)
                        .eq(Tenant::getDeleteStatus, Boolean.FALSE)
                        .eq(Tenant::getStatus, Boolean.TRUE))
                .stream()
                .collect(Collectors.toMap(Tenant::getId, tenant -> tenant, (left, right) -> left));

        return userTenants.stream()
                .filter(userTenant -> userTenant.getTenantId() != null && tenantMap.containsKey(userTenant.getTenantId()))
                .map(userTenant -> {
                    Tenant tenant = tenantMap.get(userTenant.getTenantId());
                    return new CurrentUserTenantDto()
                            .setTenantId(tenant.getId())
                            .setTenantCode(tenant.getTenantCode())
                            .setTenantName(tenant.getTenantName())
                            .setIdentityType(userTenant.getIdentityType())
                            .setDefaultFlag(userTenant.getDefaultFlag());
                })
                .collect(Collectors.toList());
    }

    @Override
    public void assertTenantAccessible(Long userId, Long tenantId) {
        Asserts.notNull(userId, "鐢ㄦ埛涓婚敭涓嶈兘涓虹┖");
        Asserts.notNull(tenantId, "绉熸埛涓婚敭涓嶈兘涓虹┖");
        List<String> roleCodes = getCurrentRoles(userId).stream()
                .map(Role::getRoleCode)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        boolean accessible = getAccessibleTenants(userId).stream()
                .anyMatch(tenant -> Objects.equals(tenant.getTenantId(), tenantId));
        Asserts.isTrue(accessible, "Current user cannot access target tenant");
    }

    private Long resolveCurrentTenantId(Long userId, List<String> roleCodes) {
        if (roleCodes != null && roleCodes.contains(PLATFORM_ADMIN)) {
            return null;
        }
        return getCurrentUserTenant(userId).getTenantId();
    }

    private UserTenant getCurrentUserTenant(Long userId) {
        List<UserTenant> userTenants = userTenantService.list(Wrappers.<UserTenant>lambdaQuery()
                .eq(UserTenant::getUserId, userId)
                .eq(UserTenant::getDeleteStatus, Boolean.FALSE)
                .eq(UserTenant::getStatus, Boolean.TRUE)
                .orderByDesc(UserTenant::getDefaultFlag)
                .orderByAsc(UserTenant::getId));
        Asserts.isTrue(!userTenants.isEmpty(), "当前用户未绑定有效租户");
        return userTenants.get(0);
    }

    private List<Role> getCurrentRoles(Long userId) {
        List<UserRole> userRoles = userRoleService.list(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getDeleteStatus, Boolean.FALSE)
                .eq(UserRole::getStatus, Boolean.TRUE));
        if (userRoles.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }

        return roleService.list(Wrappers.<Role>lambdaQuery()
                        .in(Role::getId, roleIds)
                        .eq(Role::getDeleteStatus, Boolean.FALSE)
                        .eq(Role::getStatus, Boolean.TRUE)
                        .orderByAsc(Role::getId))
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private List<MenuDto> getCurrentMenus(List<Role> roles) {
        if (roles.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> roleIds = roles.stream()
                .map(Role::getId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery()
                .in(RoleMenu::getRoleId, roleIds)
                .eq(RoleMenu::getDeleteStatus, Boolean.FALSE)
                .eq(RoleMenu::getStatus, Boolean.TRUE));
        if (roleMenus.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> menuIds = roleMenus.stream()
                .map(RoleMenu::getMenuId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (menuIds.isEmpty()) {
            return Collections.emptyList();
        }

        return MenuConverter.INSTANCE.domains2Dtos(menuService.list(Wrappers.<Menu>lambdaQuery()
                        .in(Menu::getId, menuIds)
                        .eq(Menu::getDeleteStatus, Boolean.FALSE)
                        .eq(Menu::getStatus, Boolean.TRUE)
                        .orderByAsc(Menu::getSortNo)
                        .orderByAsc(Menu::getId)))
                .stream()
                .filter(menu -> !roles.stream().anyMatch(role -> PLATFORM_ADMIN.equals(role.getRoleCode()))
                        || PLATFORM_MENU_SCOPE.equals(menu.getMenuScope()))
                .sorted(Comparator.comparing(MenuDto::getSortNo, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(MenuDto::getId, Comparator.nullsLast(Long::compareTo)))
                .collect(Collectors.toList());
    }
}
