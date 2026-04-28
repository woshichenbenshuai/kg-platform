package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.RoleMenuConverter;
import com.kgplatform.system.domain.dto.RoleMenuDto;
import com.kgplatform.system.domain.po.RoleMenu;
import com.kgplatform.system.domain.vo.RoleMenuVo;
import com.kgplatform.system.service.IRoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色菜单关系控制层
 * <p>
 * RoleMenuResource控制层
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Validated
@RestController
@Tag(name = "RoleMenuResource", description = "角色菜单关系")
@RequestMapping(path = "/role-menus", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleMenuResource {

    private final RoleMenuConverter roleMenuConverter;
    private final IRoleMenuService roleMenuService;

    public RoleMenuResource(IRoleMenuService roleMenuService, RoleMenuConverter roleMenuConverter) {
        this.roleMenuService = roleMenuService;
        this.roleMenuConverter = roleMenuConverter;
    }


    /**
     * 分页查询角色菜单关系
     *
     * @param current 当前页码
     * @param size    每页条数
     * @param vo      入参
     * @return 接口结果
     */
    @GetMapping("/pages")
    @Operation(summary = "分页查询角色菜单关系")
    public Result<Page<RoleMenuDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            RoleMenuVo vo) {
        return Result.ok(this.roleMenuService.selectPage(current, size, vo));
    }


    /**
     * 根据角色和菜单查询关系是否重复
     *
     * @param roleId 角色ID
     * @param menuId 菜单ID
     * @return 接口结果
     */
    @GetMapping("/codes")
    @Operation(summary = "根据角色和菜单查询关系是否重复")
    public Result<List<RoleMenu>> selectByRoleAndMenu(
            @Parameter(description = "角色ID") @RequestParam Long roleId,
            @Parameter(description = "菜单ID") @RequestParam Long menuId) {
        return Result.ok(this.roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery()
                .eq(RoleMenu::getRoleId, roleId)
                .eq(RoleMenu::getMenuId, menuId)
                .eq(RoleMenu::getStatus, Boolean.TRUE)
                .eq(RoleMenu::getDeleteStatus, Boolean.FALSE)));
    }


    /**
     * 根据主键查询角色菜单关系
     *
     * @param id 主键
     * @return 接口结果
     */
    @GetMapping
    @Operation(summary = "根据主键查询角色菜单关系")
    public Result<RoleMenuDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(this.roleMenuConverter.domain2Dto(this.roleMenuService.getById(id)));
    }


    /**
     * 新增角色菜单关系
     *
     * @param vo vo
     * @return 接口结果
     */
    @PostMapping
    @Operation(summary = "新增角色菜单关系")
    public Result<Boolean> insert(@RequestBody RoleMenuVo vo) {
        return Result.ok(this.roleMenuService.saveRoleMenu(vo));
    }


    /**
     * 修改角色菜单关系
     *
     * @param vo vo
     * @return 接口结果
     */
    @PutMapping
    @Operation(summary = "修改角色菜单关系")
    public Result<Boolean> update(@RequestBody RoleMenuVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.roleMenuService.update(vo));
    }


    /**
     * 删除角色菜单关系
     *
     * @param id 主键
     * @return 接口结果
     */
    @DeleteMapping
    @Operation(summary = "删除角色菜单关系")
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.roleMenuService.delete(id));
    }
}
