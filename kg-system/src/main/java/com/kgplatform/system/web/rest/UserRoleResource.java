package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.UserRoleConverter;
import com.kgplatform.system.domain.dto.UserRoleDto;
import com.kgplatform.system.domain.po.UserRole;
import com.kgplatform.system.domain.vo.UserRoleVo;
import com.kgplatform.system.service.IUserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户角色关系控制层
 * <p>
 * UserRoleResource控制层
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
 */
@Validated
@RestController
@Tag(name = "UserRoleResource", description = "用户角色关系")
@RequestMapping(path = "/user-roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRoleResource {

    private final UserRoleConverter userRoleConverter;
    private final IUserRoleService userRoleService;

    public UserRoleResource(IUserRoleService userRoleService, UserRoleConverter userRoleConverter) {
        this.userRoleService = userRoleService;
        this.userRoleConverter = userRoleConverter;
    }

    /**
     * 分页查询用户角色关系
     *
     * @param current 当前页码
     * @param size    每页条数
     * @param vo      查询条件
     * @return 分页结果
     */
    @GetMapping("/pages")
    @Operation(summary = "分页查询用户角色关系")
    public Result<Page<UserRoleDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            UserRoleVo vo) {
        return Result.ok(this.userRoleService.selectPage(current, size, vo));
    }

    /**
     * 根据用户和角色查询关系是否重复
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 查询结果
     */
    @GetMapping("/codes")
    @Operation(summary = "根据用户和角色查询关系是否重复")
    public Result<List<UserRole>> selectByUserAndRole(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "角色ID") @RequestParam Long roleId) {
        return Result.ok(this.userRoleService.list(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, roleId)
                .eq(UserRole::getStatus, Boolean.TRUE)
                .eq(UserRole::getDeleteStatus, Boolean.FALSE)));
    }

    /**
     * 根据主键查询用户角色关系
     *
     * @param id 主键
     * @return 查询结果
     */
    @GetMapping
    @Operation(summary = "根据主键查询用户角色关系")
    public Result<UserRoleDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(this.userRoleConverter.domain2Dto(this.userRoleService.getById(id)));
    }

    /**
     * 新增用户角色关系
     *
     * @param vo 入参
     * @return 新增结果
     */
    @PostMapping
    @Operation(summary = "新增用户角色关系")
    public Result<Boolean> insert(@RequestBody UserRoleVo vo) {
        return Result.ok(this.userRoleService.saveUserRole(vo));
    }

    /**
     * 修改用户角色关系
     *
     * @param vo 修改条件
     * @return 修改结果
     */
    @PutMapping
    @Operation(summary = "修改用户角色关系")
    public Result<Boolean> update(@RequestBody UserRoleVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.userRoleService.update(vo));
    }

    /**
     * 删除用户角色关系
     *
     * @param id 主键
     * @return 删除结果
     */
    @DeleteMapping
    @Operation(summary = "删除用户角色关系")
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.userRoleService.delete(id));
    }
}
