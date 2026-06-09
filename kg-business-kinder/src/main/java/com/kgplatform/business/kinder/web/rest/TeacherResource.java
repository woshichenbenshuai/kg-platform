package com.kgplatform.business.kinder.web.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.business.kinder.domain.dto.TeacherAccountOpenDto;
import com.kgplatform.business.kinder.domain.dto.TeacherDto;
import com.kgplatform.business.kinder.domain.vo.TeacherAccountOpenVo;
import com.kgplatform.business.kinder.domain.vo.TeacherVo;
import com.kgplatform.business.kinder.service.ITeacherService;
import com.kgplatform.business.kinder.service.TeacherAccountService;
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
 * Teacher controller.
 */
@Validated
@RestController
@Tag(name = "TeacherResource", description = "Teacher")
@RequestMapping(path = "/teachers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeacherResource {

    private final ITeacherService teacherService;
    private final TeacherAccountService teacherAccountService;

    public TeacherResource(ITeacherService teacherService, TeacherAccountService teacherAccountService) {
        this.teacherService = teacherService;
        this.teacherAccountService = teacherAccountService;
    }

    @GetMapping("/pages")
    @Operation(summary = "Page teachers")
    public Result<Page<TeacherDto>> selectAll(
            @Parameter(description = "Current page") @RequestParam(required = false, defaultValue = "1") Integer current,
            @Parameter(description = "Page size") @RequestParam(required = false, defaultValue = "10") Integer size,
            TeacherVo vo) {
        return Result.ok(teacherService.selectPage(current, size, vo));
    }

    @PostMapping
    @Operation(summary = "Create teacher")
    public Result<Boolean> insert(@RequestBody TeacherVo vo) {
        return Result.ok(teacherService.saveTeacher(vo));
    }

    @PostMapping("/open-account")
    @Operation(summary = "Open teacher login account")
    public Result<TeacherAccountOpenDto> openAccount(@RequestBody TeacherAccountOpenVo vo) {
        return Result.ok(teacherAccountService.openAccount(vo));
    }

    @PutMapping
    @Operation(summary = "Update teacher")
    public Result<Boolean> update(@RequestBody TeacherVo vo) {
        return Result.ok(teacherService.updateTeacher(vo));
    }

    @DeleteMapping
    @Operation(summary = "Delete teacher")
    public Result<Boolean> delete(@Parameter(description = "Teacher id") @RequestParam Long id) {
        return Result.ok(teacherService.deleteTeacher(id));
    }
}
