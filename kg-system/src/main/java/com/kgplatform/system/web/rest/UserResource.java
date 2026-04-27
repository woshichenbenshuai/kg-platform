package com.kgplatform.system.web.rest;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.UserConverter;
import com.kgplatform.system.domain.dto.UserDto;
import com.kgplatform.system.domain.po.User;
import com.kgplatform.system.domain.vo.UserVo;
import com.kgplatform.system.service.IUserService;
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
@Tag(name = "UserResource", description = "系统用户")
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private final UserConverter userConverter;
    private final IUserService userService;

    public UserResource(IUserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/pages")
    @Operation(summary = "分页查询系统用户")
    public Result<Page<UserDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "0") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            UserVo vo) {
        return Result.ok(this.userService.selectPage(current, size, vo));
    }

    @GetMapping("/names")
    @Operation(summary = "根据用户名查询用户是否重复")
    public Result<List<User>> selectByUsername(@Parameter(description = "用户名") @RequestParam String username) {
        return Result.ok(this.userService.list(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username)
                .eq(User::getDeleteStatus, Boolean.FALSE)));
    }

    @GetMapping
    @Operation(summary = "根据主键查询系统用户")
    public Result<UserDto> selectOne(@Parameter(description = "主键") @RequestParam Long id) {
        return Result.ok(this.userConverter.domain2Dto(this.userService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "新增系统用户")
    public Result<Boolean> insert(@RequestBody UserVo vo) {
        return Result.ok(this.userService.saveUser(vo));
    }

    @PutMapping
    @Operation(summary = "修改系统用户")
    public Result<Boolean> update(@RequestBody UserVo vo) {
        Asserts.notNull(vo.getId(), "主键不能为空");
        return Result.ok(this.userService.update(vo));
    }

    @DeleteMapping
    @Operation(summary = "删除系统用户")
    public Result<Boolean> delete(@Parameter(description = "主键") @RequestParam("id") Long id) {
        return Result.ok(this.userService.delete(id));
    }
}
