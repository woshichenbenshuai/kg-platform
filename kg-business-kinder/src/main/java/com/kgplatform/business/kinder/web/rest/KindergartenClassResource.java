package com.kgplatform.business.kinder.web.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.business.kinder.domain.dto.KindergartenClassDto;
import com.kgplatform.business.kinder.domain.vo.KindergartenClassVo;
import com.kgplatform.business.kinder.service.IKindergartenClassService;
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
 * 班级控制层
 */
@Validated
@RestController
@Tag(name = "KindergartenClassResource", description = "班级")
@RequestMapping(path = "/kindergarten-classes", produces = MediaType.APPLICATION_JSON_VALUE)
public class KindergartenClassResource {

    private final IKindergartenClassService classService;

    public KindergartenClassResource(IKindergartenClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/pages")
    @Operation(summary = "分页查询班级")
    public Result<Page<KindergartenClassDto>> selectAll(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "1") Integer current,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer size,
            KindergartenClassVo vo) {
        return Result.ok(classService.selectPage(current, size, vo));
    }

    @PostMapping
    @Operation(summary = "新增班级")
    public Result<Boolean> insert(@RequestBody KindergartenClassVo vo) {
        return Result.ok(classService.saveClass(vo));
    }

    @PutMapping
    @Operation(summary = "修改班级")
    public Result<Boolean> update(@RequestBody KindergartenClassVo vo) {
        return Result.ok(classService.updateClass(vo));
    }

    @DeleteMapping
    @Operation(summary = "删除班级")
    public Result<Boolean> delete(@Parameter(description = "班级主键") @RequestParam Long id) {
        return Result.ok(classService.deleteClass(id));
    }
}
