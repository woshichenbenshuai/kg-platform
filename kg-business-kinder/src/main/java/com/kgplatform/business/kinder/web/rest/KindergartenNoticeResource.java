package com.kgplatform.business.kinder.web.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.business.kinder.domain.po.KindergartenNotice;
import com.kgplatform.business.kinder.domain.vo.KindergartenNoticeVo;
import com.kgplatform.business.kinder.service.IKindergartenNoticeService;
import com.kgplatform.common.web.core.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@Tag(name = "KindergartenNoticeResource", description = "Kindergarten notice")
@RequestMapping(path = "/notices", produces = MediaType.APPLICATION_JSON_VALUE)
public class KindergartenNoticeResource {

    private final IKindergartenNoticeService noticeService;

    public KindergartenNoticeResource(IKindergartenNoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/pages")
    @Operation(summary = "Page notices")
    public Result<Page<KindergartenNotice>> selectAll(
            @Parameter(description = "Current page") @RequestParam(required = false, defaultValue = "1") Integer current,
            @Parameter(description = "Page size") @RequestParam(required = false, defaultValue = "10") Integer size,
            KindergartenNoticeVo vo) {
        return Result.ok(noticeService.selectPage(current, size, vo));
    }

    @PostMapping
    @Operation(summary = "Create notice")
    public Result<Boolean> insert(@RequestBody KindergartenNoticeVo vo) {
        return Result.ok(noticeService.saveNotice(vo));
    }

    @PutMapping
    @Operation(summary = "Update notice")
    public Result<Boolean> update(@RequestBody KindergartenNoticeVo vo) {
        return Result.ok(noticeService.updateNotice(vo));
    }

    @DeleteMapping
    @Operation(summary = "Delete notice")
    public Result<Boolean> delete(@RequestParam Long id) {
        return Result.ok(noticeService.deleteNotice(id));
    }
}
