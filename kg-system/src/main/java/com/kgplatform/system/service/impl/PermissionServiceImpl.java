package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.MenuConverter;
import com.kgplatform.system.domain.convert.PermissionPointConverter;
import com.kgplatform.system.domain.dto.CurrentUserPermissionDto;
import com.kgplatform.system.domain.dto.MenuDto;
import com.kgplatform.system.domain.dto.PermissionPointDto;
import com.kgplatform.system.domain.po.*;
import com.kgplatform.system.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("permissionService")
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class PermissionServiceImpl implements IPermissionService {

    private final IUserService userService;
    private final IUserTenantService userTenantService;
    private final IUserTenantRoleService userTenantRoleService;
    private final IRoleService roleService;
    private final IRoleMenuService roleMenuService;
    private final IMenuService menuService;
    private final IRolePermissionPointService rolePermissionPointService;
    private final IPermissionPointService permissionPointService;

    public PermissionServiceImpl(IUserService userService,
                                 IUserTenantService userTenantService,
                                 IUserTenantRoleService userTenantRoleService,
                                 IRoleService roleService,
                                 IRoleMenuService roleMenuService,
                                 IMenuService menuService,
                                 IRolePermissionPointService rolePermissionPointService,
                                 IPermissionPointService permissionPointService) {
        this.userService = userService;
        this.userTenantService = userTenantService;
        this.userTenantRoleService = userTenantRoleService;
        this.roleService = roleService;
        this.roleMenuService = roleMenuService;
        this.menuService = menuService;
        this.rolePermissionPointService = rolePermissionPointService;
        this.permissionPointService = permissionPointService;
    }

    @Override
    public CurrentUserPermissionDto getCurrentUserPermission(Long userId) {
        Asserts.notNull(userId, "用户主键不能为空");

        User user = userService.getById(userId);
        Asserts.notNull(user, "用户不存在");
        Asserts.isTrue(Boolean.FALSE.equals(user.getDeleteStatus()), "用户不存在");
        Asserts.isTrue(Integer.valueOf(1).equals(user.getStatus()), "用户已禁用");

        UserTenant userTenant = getCurrentUserTenant(userId);
        List<Role> roles = getCurrentRoles(userTenant.getId());
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
        List<PermissionPointDto> permissionPoints = getCurrentPermissionPoints(roles);
        List<String> permissionCodes = permissionPoints.stream()
                .map(PermissionPointDto::getPermissionCode)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        CurrentUserPermissionDto dto = new CurrentUserPermissionDto();
        dto.setUserId(userId);
        dto.setUsername(user.getUsername());
        dto.setTenantId(userTenant.getTenantId());
        dto.setRoleCodes(roleCodes);
        dto.setRoleNames(roleNames);
        dto.setMenus(menus);
        dto.setPermissionCodes(permissionCodes);
        dto.setPermissionPoints(permissionPoints);
        return dto;
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

    private List<Role> getCurrentRoles(Long userTenantId) {
        List<UserTenantRole> userTenantRoles = userTenantRoleService.list(Wrappers.<UserTenantRole>lambdaQuery()
                .eq(UserTenantRole::getUserTenantId, userTenantId)
                .eq(UserTenantRole::getDeleteStatus, Boolean.FALSE)
                .eq(UserTenantRole::getStatus, Boolean.TRUE));
        if (userTenantRoles.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> roleIds = userTenantRoles.stream()
                .map(UserTenantRole::getRoleId)
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
                .sorted(Comparator.comparing(MenuDto::getSortNo, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(MenuDto::getId, Comparator.nullsLast(Long::compareTo)))
                .collect(Collectors.toList());
    }

    private List<PermissionPointDto> getCurrentPermissionPoints(List<Role> roles) {
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

        List<RolePermissionPoint> rolePermissionPoints = rolePermissionPointService.list(
                Wrappers.<RolePermissionPoint>lambdaQuery()
                        .in(RolePermissionPoint::getRoleId, roleIds)
                        .eq(RolePermissionPoint::getDeleteStatus, Boolean.FALSE)
                        .eq(RolePermissionPoint::getStatus, Boolean.TRUE));
        if (rolePermissionPoints.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> permissionPointIds = rolePermissionPoints.stream()
                .map(RolePermissionPoint::getPermissionPointId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (permissionPointIds.isEmpty()) {
            return Collections.emptyList();
        }

        return PermissionPointConverter.INSTANCE.domains2Dtos(permissionPointService.list(
                        Wrappers.<PermissionPoint>lambdaQuery()
                                .in(PermissionPoint::getId, permissionPointIds)
                                .eq(PermissionPoint::getDeleteStatus, Boolean.FALSE)
                                .eq(PermissionPoint::getStatus, Boolean.TRUE)
                                .orderByAsc(PermissionPoint::getPermissionType)
                                .orderByAsc(PermissionPoint::getId)))
                .stream()
                .sorted(Comparator.comparing(PermissionPointDto::getPermissionType, Comparator.nullsLast(String::compareTo))
                        .thenComparing(PermissionPointDto::getId, Comparator.nullsLast(Long::compareTo)))
                .collect(Collectors.toList());
    }
}
