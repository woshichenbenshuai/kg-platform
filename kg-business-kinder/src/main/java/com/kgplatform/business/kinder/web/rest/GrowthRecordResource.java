package com.kgplatform.business.kinder.web.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.business.kinder.domain.po.GrowthRecord;
import com.kgplatform.business.kinder.domain.vo.GrowthRecordVo;
import com.kgplatform.business.kinder.service.IGrowthRecordService;
import com.kgplatform.common.web.core.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@Tag(name = "GrowthRecordResource", description = "Growth record")
@RequestMapping(path = "/growth-records", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrowthRecordResource {

    private final IGrowthRecordService growthRecordService;

    public GrowthRecordResource(IGrowthRecordService growthRecordService) {
        this.growthRecordService = growthRecordService;
    }

    @GetMapping("/pages")
    @Operation(summary = "Page growth records")
    public Result<Page<GrowthRecord>> selectAll(
            @Parameter(description = "Current page") @RequestParam(required = false, defaultValue = "1") Integer current,
            @Parameter(description = "Page size") @RequestParam(required = false, defaultValue = "10") Integer size,
            GrowthRecordVo vo) {
        return Result.ok(growthRecordService.selectPage(current, size, vo));
    }

    @PostMapping
    @Operation(summary = "Create growth record")
    public Result<Boolean> insert(@RequestBody GrowthRecordVo vo) {
        return Result.ok(growthRecordService.saveGrowthRecord(vo));
    }

    @PutMapping
    @Operation(summary = "Update growth record")
    public Result<Boolean> update(@RequestBody GrowthRecordVo vo) {
        return Result.ok(growthRecordService.updateGrowthRecord(vo));
    }

    @DeleteMapping
    @Operation(summary = "Delete growth record")
    public Result<Boolean> delete(@RequestParam Long id) {
        return Result.ok(growthRecordService.deleteGrowthRecord(id));
    }
}
