package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.TenantConverter;
import com.kgplatform.system.domain.dto.TenantDto;
import com.kgplatform.system.domain.po.Tenant;
import com.kgplatform.system.domain.vo.TenantVo;
import com.kgplatform.system.service.ITenantService;
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
@Tag(name = "TenantResource", description = "系统租户")
@RequestMapping(path = "/tenants", produces = MediaType.APPLICATION_JSON_VALUE)
public class TenantResource {

    private final TenantConverter tenantConverter;
    private final ITenantService tenantService;

    public TenantResource(ITenantService tenantService, TenantConverter tenantConverter) {
        this.tenantService = tenantService;
        this.tenantConverter = tenantConverter;
    }

    @GetMapping("/pages")
    @Operation(summary = "分页查询系统租户")
    public Result<Page<TenantDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            TenantVo vo) {
        return Result.ok(this.tenantService.selectPage(current, size, vo));
    }

    @GetMapping("/codes")
    @Operation(summary = "根据编码查询租户是否重复")
    public Result<List<Tenant>> selectByCode(@Parameter(description = "租户编码") @RequestParam String code) {
        return Result.ok(this.tenantService.list(Wrappers.<Tenant>lambdaQuery()
                .eq(Tenant::getTenantCode, code)
                .eq(Tenant::getStatus, Boolean.TRUE)
                .eq(Tenant::getDeleteStatus, Boolean.FALSE)));
    }

    @GetMapping
    @Operation(summary = "根据主键查询系统租户")
    public Result<TenantDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(this.tenantConverter.domain2Dto(this.tenantService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "新增系统租户")
    public Result<Boolean> insert(@RequestBody TenantVo vo) {
        return Result.ok(this.tenantService.saveTenant(vo));
    }

    @PutMapping
    @Operation(summary = "修改系统租户")
    public Result<Boolean> update(@RequestBody TenantVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.tenantService.update(vo));
    }

    @DeleteMapping
    @Operation(summary = "删除系统租户")
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.tenantService.delete(id));
    }
}
