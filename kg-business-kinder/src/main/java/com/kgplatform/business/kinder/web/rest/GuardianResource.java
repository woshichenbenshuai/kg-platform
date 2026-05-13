package com.kgplatform.business.kinder.web.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.business.kinder.domain.dto.GuardianDto;
import com.kgplatform.business.kinder.domain.dto.ParentAccountOpenDto;
import com.kgplatform.business.kinder.domain.vo.GuardianVo;
import com.kgplatform.business.kinder.domain.vo.ParentAccountOpenVo;
import com.kgplatform.business.kinder.service.IGuardianService;
import com.kgplatform.business.kinder.service.ParentAccountService;
import com.kgplatform.common.web.core.Result;
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

/**
 * 家长控制层
 */
@Validated
@RestController
@Tag(name = "GuardianResource", description = "家长")
@RequestMapping(path = "/guardians", produces = MediaType.APPLICATION_JSON_VALUE)
public class GuardianResource {

    private final IGuardianService guardianService;
    private final ParentAccountService parentAccountService;

    public GuardianResource(IGuardianService guardianService, ParentAccountService parentAccountService) {
        this.guardianService = guardianService;
        this.parentAccountService = parentAccountService;
    }

    @GetMapping("/pages")
    @Operation(summary = "分页查询家长")
    public Result<Page<GuardianDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "1") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            GuardianVo vo) {
        return Result.ok(guardianService.selectPage(current, size, vo));
    }

    @PostMapping
    @Operation(summary = "新增家长")
    public Result<Boolean> insert(@RequestBody GuardianVo vo) {
        return Result.ok(guardianService.saveGuardian(vo));
    }

    @PostMapping("/open-account")
    @Operation(summary = "开通家长登录账号")
    public Result<ParentAccountOpenDto> openAccount(@RequestBody ParentAccountOpenVo vo) {
        return Result.ok(parentAccountService.openAccount(vo));
    }

    @PutMapping
    @Operation(summary = "修改家长")
    public Result<Boolean> update(@RequestBody GuardianVo vo) {
        return Result.ok(guardianService.updateGuardian(vo));
    }

    @DeleteMapping
    @Operation(summary = "删除家长")
    public Result<Boolean> delete(@Parameter(description = "家长主键") @RequestParam Long id) {
        return Result.ok(guardianService.deleteGuardian(id));
    }
}
