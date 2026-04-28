package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.MenuConverter;
import com.kgplatform.system.domain.dto.MenuDto;
import com.kgplatform.system.domain.po.Menu;
import com.kgplatform.system.domain.vo.MenuVo;
import com.kgplatform.system.service.IMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统菜单控制层
 * <p>
 * MenuResource控制层
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Validated
@RestController
@Tag(name = "MenuResource", description = "系统菜单")
@RequestMapping(path = "/menus", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuResource {

    private final MenuConverter menuConverter;
    private final IMenuService menuService;

    public MenuResource(IMenuService menuService, MenuConverter menuConverter) {
        this.menuService = menuService;
        this.menuConverter = menuConverter;
    }


    /**
     * 分页查询系统菜单
     *
     * @param current 当前页码
     * @param size    每页条数
     * @param vo      入参
     * @return 接口结果
     */
    @GetMapping("/pages")
    @Operation(summary = "分页查询系统菜单")
    public Result<Page<MenuDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            MenuVo vo) {
        return Result.ok(this.menuService.selectPage(current, size, vo));
    }


    /**
     * 根据编码查询菜单是否重复
     *
     * @param code 菜单编码
     * @return 接口结果
     */
    @GetMapping("/codes")
    @Operation(summary = "根据编码查询菜单是否重复")
    public Result<List<Menu>> selectByCode(@Parameter(description = "菜单编码") @RequestParam String code) {
        return Result.ok(this.menuService.list(Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getMenuCode, code)
                .eq(Menu::getStatus, Boolean.TRUE)
                .eq(Menu::getDeleteStatus, Boolean.FALSE)));
    }


    /**
     * 根据主键查询系统菜单
     *
     * @param id 主键
     * @return 接口结果
     */
    @GetMapping
    @Operation(summary = "根据主键查询系统菜单")
    public Result<MenuDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(this.menuConverter.domain2Dto(this.menuService.getById(id)));
    }


    /**
     * 新增系统菜单
     *
     * @param vo vo
     * @return 接口结果
     */
    @PostMapping
    @Operation(summary = "新增系统菜单")
    public Result<Boolean> insert(@RequestBody MenuVo vo) {
        return Result.ok(this.menuService.saveMenu(vo));
    }


    /**
     * 修改系统菜单
     *
     * @param vo vo
     * @return 接口结果
     */
    @PutMapping
    @Operation(summary = "修改系统菜单")
    public Result<Boolean> update(@RequestBody MenuVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.menuService.update(vo));
    }


    /**
     * 删除系统菜单
     *
     * @param id 主键
     * @return 接口结果
     */
    @DeleteMapping
    @Operation(summary = "删除系统菜单")
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.menuService.delete(id));
    }
}
