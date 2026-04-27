package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.UserRoleConverter;
import com.kgplatform.system.domain.dto.UserRoleDto;
import com.kgplatform.system.domain.po.UserRole;
import com.kgplatform.system.domain.vo.UserRoleVo;
import com.kgplatform.system.service.IUserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@Tag(name = "UserRoleResource", description = "User role relation")
@RequestMapping(path = "/user-roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRoleResource {

    private final UserRoleConverter userRoleConverter;
    private final IUserRoleService userRoleService;

    public UserRoleResource(IUserRoleService userRoleService, UserRoleConverter userRoleConverter) {
        this.userRoleService = userRoleService;
        this.userRoleConverter = userRoleConverter;
    }

    @GetMapping("/pages")
    @Operation(summary = "Page query user role relations")
    public Result<Page<UserRoleDto>> selectAll(
            @Parameter(description = "Current page") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "Page size") @RequestParam(required = false, defaultValue = "10") Integer size,
            UserRoleVo vo) {
        return Result.ok(this.userRoleService.selectPage(current, size, vo));
    }

    @GetMapping("/codes")
    @Operation(summary = "Query relation by user and role")
    public Result<List<UserRole>> selectByUserAndRole(
            @Parameter(description = "User id") @RequestParam Long userId,
            @Parameter(description = "Role id") @RequestParam Long roleId) {
        return Result.ok(this.userRoleService.list(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, roleId)
                .eq(UserRole::getStatus, Boolean.TRUE)
                .eq(UserRole::getDeleteStatus, Boolean.FALSE)));
    }

    @GetMapping
    @Operation(summary = "Query one user role relation")
    public Result<UserRoleDto> selectOne(@Parameter(description = "Id") @RequestParam Long id) {
        return Result.ok(this.userRoleConverter.domain2Dto(this.userRoleService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "Create user role relation")
    public Result<Boolean> insert(@RequestBody UserRoleVo vo) {
        return Result.ok(this.userRoleService.saveUserRole(vo));
    }

    @PutMapping
    @Operation(summary = "Update user role relation")
    public Result<Boolean> update(@RequestBody UserRoleVo vo) {
        Asserts.notNull(vo.getId(), "Id can not be null");
        return Result.ok(this.userRoleService.update(vo));
    }

    @DeleteMapping
    @Operation(summary = "Delete user role relation")
    public Result<Boolean> delete(@Parameter(description = "Id") @RequestParam("id") Long id) {
        return Result.ok(this.userRoleService.delete(id));
    }
}
