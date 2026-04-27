package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.UserTenantRoleConverter;
import com.kgplatform.system.domain.dto.UserTenantRoleDto;
import com.kgplatform.system.domain.po.UserTenantRole;
import com.kgplatform.system.domain.vo.UserTenantRoleVo;
import com.kgplatform.system.service.IUserTenantRoleService;
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
@Tag(name = "UserTenantRoleResource", description = "用户租户角色关系")
@RequestMapping(path = "/user-tenant-roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserTenantRoleResource {

    private final UserTenantRoleConverter userTenantRoleConverter;
    private final IUserTenantRoleService userTenantRoleService;

    public UserTenantRoleResource(IUserTenantRoleService userTenantRoleService,
                                  UserTenantRoleConverter userTenantRoleConverter) {
        this.userTenantRoleService = userTenantRoleService;
        this.userTenantRoleConverter = userTenantRoleConverter;
    }

    @GetMapping("/pages")
    @Operation(summary = "分页查询用户租户角色关系")
    public Result<Page<UserTenantRoleDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            UserTenantRoleVo vo) {
        return Result.ok(this.userTenantRoleService.selectPage(current, size, vo));
    }

    @GetMapping("/codes")
    @Operation(summary = "根据用户租户关系和角色查询关系是否重复")
    public Result<List<UserTenantRole>> selectByUserTenantAndRole(
            @Parameter(description = "用户租户关系ID") @RequestParam Long userTenantId,
            @Parameter(description = "角色ID") @RequestParam Long roleId) {
        return Result.ok(this.userTenantRoleService.list(Wrappers.<UserTenantRole>lambdaQuery()
                .eq(UserTenantRole::getUserTenantId, userTenantId)
                .eq(UserTenantRole::getRoleId, roleId)
                .eq(UserTenantRole::getStatus, Boolean.TRUE)
                .eq(UserTenantRole::getDeleteStatus, Boolean.FALSE)));
    }

    @GetMapping
    @Operation(summary = "根据主键查询用户租户角色关系")
    public Result<UserTenantRoleDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(this.userTenantRoleConverter.domain2Dto(this.userTenantRoleService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "新增用户租户角色关系")
    public Result<Boolean> insert(@RequestBody UserTenantRoleVo vo) {
        return Result.ok(this.userTenantRoleService.saveUserTenantRole(vo));
    }

    @PutMapping
    @Operation(summary = "修改用户租户角色关系")
    public Result<Boolean> update(@RequestBody UserTenantRoleVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.userTenantRoleService.update(vo));
    }

    @DeleteMapping
    @Operation(summary = "删除用户租户角色关系")
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.userTenantRoleService.delete(id));
    }
}
