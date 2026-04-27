package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.PermissionPointConverter;
import com.kgplatform.system.domain.dto.PermissionPointDto;
import com.kgplatform.system.domain.po.PermissionPoint;
import com.kgplatform.system.domain.vo.PermissionPointVo;
import com.kgplatform.system.service.IPermissionPointService;
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
@Tag(name = "PermissionPointResource", description = "系统权限点")
@RequestMapping(path = "/permission-points", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionPointResource {

    private final PermissionPointConverter permissionPointConverter;
    private final IPermissionPointService permissionPointService;

    public PermissionPointResource(IPermissionPointService permissionPointService,
                                   PermissionPointConverter permissionPointConverter) {
        this.permissionPointService = permissionPointService;
        this.permissionPointConverter = permissionPointConverter;
    }

    @GetMapping("/pages")
    @Operation(summary = "分页查询系统权限点")
    public Result<Page<PermissionPointDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            PermissionPointVo vo) {
        return Result.ok(this.permissionPointService.selectPage(current, size, vo));
    }

    @GetMapping("/codes")
    @Operation(summary = "根据编码查询权限点是否重复")
    public Result<List<PermissionPoint>> selectByCode(@Parameter(description = "权限编码") @RequestParam String code) {
        return Result.ok(this.permissionPointService.list(Wrappers.<PermissionPoint>lambdaQuery()
                .eq(PermissionPoint::getPermissionCode, code)
                .eq(PermissionPoint::getStatus, Boolean.TRUE)
                .eq(PermissionPoint::getDeleteStatus, Boolean.FALSE)));
    }

    @GetMapping
    @Operation(summary = "根据主键查询系统权限点")
    public Result<PermissionPointDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(this.permissionPointConverter.domain2Dto(this.permissionPointService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "新增系统权限点")
    public Result<Boolean> insert(@RequestBody PermissionPointVo vo) {
        return Result.ok(this.permissionPointService.savePermissionPoint(vo));
    }

    @PutMapping
    @Operation(summary = "修改系统权限点")
    public Result<Boolean> update(@RequestBody PermissionPointVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.permissionPointService.update(vo));
    }

    @DeleteMapping
    @Operation(summary = "删除系统权限点")
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.permissionPointService.delete(id));
    }
}
