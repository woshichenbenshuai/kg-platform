package com.kgplatform.business.kinder.web.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.business.kinder.domain.dto.StudentDetailDto;
import com.kgplatform.business.kinder.domain.dto.StudentDto;
import com.kgplatform.business.kinder.domain.vo.StudentVo;
import com.kgplatform.business.kinder.service.IStudentService;
import com.kgplatform.common.web.core.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生控制层
 */
@Validated
@RestController
@Tag(name = "StudentResource", description = "学生")
@RequestMapping(path = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentResource {

    private final IStudentService studentService;

    public StudentResource(IStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/pages")
    @Operation(summary = "分页查询学生")
    public Result<Page<StudentDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "1") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            StudentVo vo) {
        return Result.ok(studentService.selectPage(current, size, vo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询学生详情")
    public Result<StudentDetailDto> selectDetail(@Parameter(description = "学生主键") @PathVariable Long id) {
        return Result.ok(studentService.selectDetail(id));
    }

    @GetMapping("/current-database")
    @Operation(summary = "查询当前租户命中的数据库")
    public Result<String> currentDatabase() {
        return Result.ok(studentService.currentDatabase());
    }
}
