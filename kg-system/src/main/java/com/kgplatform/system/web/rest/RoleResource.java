package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.RoleConverter;
import com.kgplatform.system.domain.dto.RoleDto;
import com.kgplatform.system.domain.po.Role;
import com.kgplatform.system.domain.vo.RoleVo;
import com.kgplatform.system.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 系统角色控制层
 * <p>
 * RoleResource控制层
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Validated
@RestController
@Tag(name = "RoleResource", description = "系统角色")
@RequestMapping(path = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleResource {

    private final RoleConverter roleConverter;
    private final IRoleService roleService;

    public RoleResource(IRoleService roleService, RoleConverter roleConverter) {
        this.roleService = roleService;
        this.roleConverter = roleConverter;
    }

    @GetMapping("/pages")
    @Operation(summary = "分页查询系统角色")
    /**
     * 分页查询系统角色
     *
     * @param false 当前页码
     * @param false 每页条数
     * @return 接口结果
     */
    public Result<Page<RoleDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            RoleVo vo) {
        return Result.ok(this.roleService.selectPage(current, size, vo));
    }

    @GetMapping("/codes")
    @Operation(summary = "根据编码查询角色是否重复")
    /**
     * 根据编码查询角色是否重复
     *
     * @param code 角色编码
     * @return 接口结果
     */
    public Result<List<Role>> selectByCode(@Parameter(description = "角色编码") @RequestParam String code) {
        return Result.ok(this.roleService.list(Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleCode, code)
                .eq(Role::getStatus, Boolean.TRUE)
                .eq(Role::getDeleteStatus, Boolean.FALSE)));
    }

    @GetMapping
    @Operation(summary = "根据主键查询系统角色")
    /**
     * 根据主键查询系统角色
     *
     * @param id 主键
     * @return 接口结果
     */
    public Result<RoleDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(this.roleConverter.domain2Dto(this.roleService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "新增系统角色")
    /**
     * 新增系统角色
     *
     * @param vo vo
     * @return 接口结果
     */
    public Result<Boolean> insert(@RequestBody RoleVo vo) {
        return Result.ok(this.roleService.saveRole(vo));
    }

    @PutMapping
    @Operation(summary = "修改系统角色")
    /**
     * 修改系统角色
     *
     * @param vo vo
     * @return 接口结果
     */
    public Result<Boolean> update(@RequestBody RoleVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.roleService.update(vo));
    }

    @DeleteMapping
    @Operation(summary = "删除系统角色")
    /**
     * 删除系统角色
     *
     * @param id 主键
     * @return 接口结果
     */
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.roleService.delete(id));
    }
}
