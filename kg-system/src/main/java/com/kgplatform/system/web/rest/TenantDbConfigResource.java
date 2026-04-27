package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.TenantDbConfigConverter;
import com.kgplatform.system.domain.dto.TenantDbConfigDto;
import com.kgplatform.system.domain.po.TenantDbConfig;
import com.kgplatform.system.domain.vo.TenantDbConfigVo;
import com.kgplatform.system.service.ITenantDbConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@Tag(name = "TenantDbConfigResource", description = "租户数据库配置")
@RequestMapping(path = "/tenant-db-configs", produces = MediaType.APPLICATION_JSON_VALUE)
public class TenantDbConfigResource {

    private final ITenantDbConfigService tenantDbConfigService;

    public TenantDbConfigResource(ITenantDbConfigService tenantDbConfigService) {
        this.tenantDbConfigService = tenantDbConfigService;
    }

    @GetMapping("/pages")
    @Operation(summary = "分页查询租户数据库配置")
    public Result<Page<TenantDbConfigDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            TenantDbConfigVo vo) {
        return Result.ok(this.tenantDbConfigService.selectPage(current, size, vo));
    }

    @GetMapping("/tenant")
    @Operation(summary = "根据租户查询数据库配置是否重复")
    public Result<Boolean> selectByTenantId(@Parameter(description = "租户ID") @RequestParam Long tenantId) {
        return Result.ok(this.tenantDbConfigService.count(new LambdaQueryWrapper<TenantDbConfig>()
                .eq(TenantDbConfig::getTenantId, tenantId)
                .eq(TenantDbConfig::getDeleteStatus, Boolean.FALSE)) > 0);
    }

    @GetMapping
    @Operation(summary = "根据主键查询租户数据库配置")
    public Result<TenantDbConfigDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(TenantDbConfigConverter.INSTANCE.domain2Dto(this.tenantDbConfigService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "新增租户数据库配置")
    public Result<Boolean> insert(@RequestBody TenantDbConfigVo vo) {
        return Result.ok(this.tenantDbConfigService.saveTenantDbConfig(vo));
    }

    @PutMapping
    @Operation(summary = "修改租户数据库配置")
    public Result<Boolean> update(@RequestBody TenantDbConfigVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.tenantDbConfigService.update(vo));
    }

    @DeleteMapping
    @Operation(summary = "删除租户数据库配置")
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.tenantDbConfigService.delete(id));
    }

    @PostMapping("/test-connection")
    @Operation(summary = "测试租户数据库连接")
    public Result<String> testConnection(@RequestBody TenantDbConfigVo vo) {
        return Result.ok(this.tenantDbConfigService.testConnection(vo));
    }

    @GetMapping("/schema-version")
    @Operation(summary = "查询租户子库版本")
    public Result<String> schemaVersion(@Parameter(description = "租户ID") @RequestParam Long tenantId) {
        return Result.ok(this.tenantDbConfigService.getSchemaVersion(tenantId));
    }
}
