package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.UserTenantConverter;
import com.kgplatform.system.domain.dto.UserTenantDto;
import com.kgplatform.system.domain.po.UserTenant;
import com.kgplatform.system.domain.vo.UserTenantVo;
import com.kgplatform.system.service.IUserTenantService;
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
@Tag(name = "UserTenantResource", description = "用户租户关系")
@RequestMapping(path = "/user-tenants", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserTenantResource {

    private final UserTenantConverter userTenantConverter;
    private final IUserTenantService userTenantService;

    public UserTenantResource(IUserTenantService userTenantService, UserTenantConverter userTenantConverter) {
        this.userTenantService = userTenantService;
        this.userTenantConverter = userTenantConverter;
    }

    @GetMapping("/pages")
    @Operation(summary = "分页查询用户租户关系")
    public Result<Page<UserTenantDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            UserTenantVo vo) {
        return Result.ok(this.userTenantService.selectPage(current, size, vo));
    }

    @GetMapping("/codes")
    @Operation(summary = "根据用户和租户查询关系是否重复")
    public Result<List<UserTenant>> selectByUserAndTenant(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "租户ID") @RequestParam Long tenantId) {
        return Result.ok(this.userTenantService.list(Wrappers.<UserTenant>lambdaQuery()
                .eq(UserTenant::getUserId, userId)
                .eq(UserTenant::getTenantId, tenantId)
                .eq(UserTenant::getStatus, Boolean.TRUE)
                .eq(UserTenant::getDeleteStatus, Boolean.FALSE)));
    }

    @GetMapping
    @Operation(summary = "根据主键查询用户租户关系")
    public Result<UserTenantDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(this.userTenantConverter.domain2Dto(this.userTenantService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "新增用户租户关系")
    public Result<Boolean> insert(@RequestBody UserTenantVo vo) {
        return Result.ok(this.userTenantService.saveUserTenant(vo));
    }

    @PutMapping
    @Operation(summary = "修改用户租户关系")
    public Result<Boolean> update(@RequestBody UserTenantVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.userTenantService.update(vo));
    }

    @DeleteMapping
    @Operation(summary = "删除用户租户关系")
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.userTenantService.delete(id));
    }
}
