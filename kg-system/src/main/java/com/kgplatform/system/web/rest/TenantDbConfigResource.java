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
import com.kgplatform.system.web.TenantScopeHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * ç§æ·æ°æ®åºé
 * ç½®æ§å¶å±
 * <p>
 * TenantDbConfigResourceæ§å¶å±
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Validated
@RestController
@Tag(name = "TenantDbConfigResource", description = "租户数据库配置")
@RequestMapping(path = "/tenant-db-configs", produces = MediaType.APPLICATION_JSON_VALUE)
public class TenantDbConfigResource {

    private final ITenantDbConfigService tenantDbConfigService;
    private final TenantScopeHelper tenantScopeHelper;

    public TenantDbConfigResource(ITenantDbConfigService tenantDbConfigService,
                                  TenantScopeHelper tenantScopeHelper) {
        this.tenantDbConfigService = tenantDbConfigService;
        this.tenantScopeHelper = tenantScopeHelper;
    }


    /**
     * 分页查询租户数据库配置
     *
     * @param current 当前页码
     * @param size    每页条数
     * @param vo      入参
     * @return 接口结果
     */
    @GetMapping("/pages")
    @Operation(summary = "分页查询租户数据库配置")
    public Result<Page<TenantDbConfigDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            TenantDbConfigVo vo) {
        TenantDbConfigVo query = vo == null ? new TenantDbConfigVo() : vo;
        query.setBindTenantId(tenantScopeHelper.resolveTenantId(query.getBindTenantId()));
        return Result.ok(this.tenantDbConfigService.selectPage(current, size, query));
    }


    /**
     * 根据租户查询数据库配置是否重复
     *
     * @param tenantId 租户ID
     * @return 接口结果
     */
    @GetMapping("/tenant")
    @Operation(summary = "根据租户查询数据库配置是否重复")
    public Result<Boolean> selectByTenantId(@Parameter(description = "租户ID") @RequestParam(required = false) Long tenantId) {
        Long scopedTenantId = tenantScopeHelper.resolveTenantId(tenantId);
        return Result.ok(this.tenantDbConfigService.count(new LambdaQueryWrapper<TenantDbConfig>()
                .eq(TenantDbConfig::getTenantId, scopedTenantId)
                .eq(TenantDbConfig::getDeleteStatus, Boolean.FALSE)) > 0);
    }


    /**
     * 根据主键查询租户数据库配置
     *
     * @param id 主键
     * @return 接口结果
     */
    @GetMapping
    @Operation(summary = "根据主键查询租户数据库配置")
    public Result<TenantDbConfigDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        TenantDbConfig entity = this.tenantDbConfigService.getById(id);
        Asserts.notNull(entity, "租户数据库配置不存在");
        tenantScopeHelper.assertAccessible(entity.getTenantId());
        return Result.ok(TenantDbConfigConverter.INSTANCE.domain2Dto(entity));
    }


    /**
     * 新增租户数据库配置
     *
     * @param vo vo
     * @return 接口结果
     */
    @PostMapping
    @Operation(summary = "新增租户数据库配置")
    public Result<Boolean> insert(@RequestBody TenantDbConfigVo vo) {
        vo.setBindTenantId(tenantScopeHelper.resolveTenantId(vo.getBindTenantId()));
        return Result.ok(this.tenantDbConfigService.saveTenantDbConfig(vo));
    }


    /**
     * 修改租户数据库配置
     *
     * @param vo vo
     * @return 接口结果
     */
    @PutMapping
    @Operation(summary = "修改租户数据库配置")
    public Result<Boolean> update(@RequestBody TenantDbConfigVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        TenantDbConfig entity = this.tenantDbConfigService.getById(vo.getId());
        Asserts.notNull(entity, "租户数据库配置不存在");
        tenantScopeHelper.assertAccessible(entity.getTenantId());
        if (vo.getBindTenantId() == null) {
            vo.setBindTenantId(entity.getTenantId());
        } else {
            tenantScopeHelper.assertAccessible(vo.getBindTenantId());
        }
        return Result.ok(this.tenantDbConfigService.update(vo));
    }


    /**
     * 删除租户数据库配置
     *
     * @param id 主键
     * @return 接口结果
     */
    @DeleteMapping
    @Operation(summary = "删除租户数据库配置")
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        TenantDbConfig entity = this.tenantDbConfigService.getById(id);
        Asserts.notNull(entity, "租户数据库配置不存在");
        tenantScopeHelper.assertAccessible(entity.getTenantId());
        return Result.ok(this.tenantDbConfigService.delete(id));
    }


    /**
     * 测试租户数据库连接
     *
     * @param vo vo
     * @return 接口结果
     */
    @PostMapping("/test-connection")
    @Operation(summary = "测试租户数据库连接")
    public Result<String> testConnection(@RequestBody TenantDbConfigVo vo) {
        if (vo.getId() != null) {
            TenantDbConfig entity = this.tenantDbConfigService.getById(vo.getId());
            Asserts.notNull(entity, "租户数据库配置不存在");
            tenantScopeHelper.assertAccessible(entity.getTenantId());
            if (vo.getBindTenantId() == null) {
                vo.setBindTenantId(entity.getTenantId());
            }
        } else {
            vo.setBindTenantId(tenantScopeHelper.resolveTenantId(vo.getBindTenantId()));
        }
        return Result.ok(this.tenantDbConfigService.testConnection(vo));
    }


    /**
     * 查询租户子库版本
     *
     * @param tenantId 租户ID
     * @return 接口结果
     */
    @GetMapping("/schema-version")
    @Operation(summary = "查询租户子库版本")
    public Result<String> schemaVersion(@Parameter(description = "租户ID") @RequestParam(required = false) Long tenantId) {
        return Result.ok(this.tenantDbConfigService.getSchemaVersion(tenantScopeHelper.resolveTenantId(tenantId)));
    }
}
