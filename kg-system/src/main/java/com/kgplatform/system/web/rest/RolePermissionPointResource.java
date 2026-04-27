package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.RolePermissionPointConverter;
import com.kgplatform.system.domain.dto.RolePermissionPointDto;
import com.kgplatform.system.domain.po.RolePermissionPoint;
import com.kgplatform.system.domain.vo.RolePermissionPointVo;
import com.kgplatform.system.service.IRolePermissionPointService;
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
@Tag(name = "RolePermissionPointResource", description = "角色权限点关系")
@RequestMapping(path = "/role-permission-points", produces = MediaType.APPLICATION_JSON_VALUE)
public class RolePermissionPointResource {

    private final RolePermissionPointConverter rolePermissionPointConverter;
    private final IRolePermissionPointService rolePermissionPointService;

    public RolePermissionPointResource(IRolePermissionPointService rolePermissionPointService,
                                       RolePermissionPointConverter rolePermissionPointConverter) {
        this.rolePermissionPointService = rolePermissionPointService;
        this.rolePermissionPointConverter = rolePermissionPointConverter;
    }

    @GetMapping("/pages")
    @Operation(summary = "分页查询角色权限点关系")
    public Result<Page<RolePermissionPointDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            RolePermissionPointVo vo) {
        return Result.ok(this.rolePermissionPointService.selectPage(current, size, vo));
    }

    @GetMapping("/codes")
    @Operation(summary = "根据角色和权限点查询关系是否重复")
    public Result<List<RolePermissionPoint>> selectByCode(
            @Parameter(description = "角色ID") @RequestParam Long roleId,
            @Parameter(description = "权限点ID") @RequestParam Long permissionPointId) {
        return Result.ok(this.rolePermissionPointService.list(Wrappers.<RolePermissionPoint>lambdaQuery()
                .eq(RolePermissionPoint::getRoleId, roleId)
                .eq(RolePermissionPoint::getPermissionPointId, permissionPointId)
                .eq(RolePermissionPoint::getStatus, Boolean.TRUE)
                .eq(RolePermissionPoint::getDeleteStatus, Boolean.FALSE)));
    }

    @GetMapping
    @Operation(summary = "根据主键查询角色权限点关系")
    public Result<RolePermissionPointDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(this.rolePermissionPointConverter.domain2Dto(this.rolePermissionPointService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "新增角色权限点关系")
    public Result<Boolean> insert(@RequestBody RolePermissionPointVo vo) {
        return Result.ok(this.rolePermissionPointService.saveRolePermissionPoint(vo));
    }

    @PutMapping
    @Operation(summary = "修改角色权限点关系")
    public Result<Boolean> update(@RequestBody RolePermissionPointVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.rolePermissionPointService.update(vo));
    }

    @DeleteMapping
    @Operation(summary = "删除角色权限点关系")
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.rolePermissionPointService.delete(id));
    }
}
