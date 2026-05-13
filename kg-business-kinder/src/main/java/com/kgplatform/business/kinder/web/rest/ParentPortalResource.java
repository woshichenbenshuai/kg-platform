package com.kgplatform.business.kinder.web.rest;

import com.kgplatform.business.kinder.domain.dto.ParentChildDto;
import com.kgplatform.business.kinder.domain.dto.ParentGrowthRecordDto;
import com.kgplatform.business.kinder.domain.dto.ParentHomeDto;
import com.kgplatform.business.kinder.domain.dto.ParentLeaveRequestDto;
import com.kgplatform.business.kinder.domain.dto.ParentNoticeDto;
import com.kgplatform.business.kinder.domain.dto.ParentRecipeDto;
import com.kgplatform.business.kinder.domain.vo.ParentLeaveRequestVo;
import com.kgplatform.business.kinder.service.IParentPortalService;
import com.kgplatform.common.web.core.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * 家长端控制层
 */
@Validated
@RestController
@Tag(name = "ParentPortalResource", description = "家长端")
@RequestMapping(path = "/parent", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParentPortalResource {

    private final IParentPortalService parentPortalService;

    public ParentPortalResource(IParentPortalService parentPortalService) {
        this.parentPortalService = parentPortalService;
    }

    @GetMapping("/home")
    @Operation(summary = "家长端首页")
    public Result<ParentHomeDto> home() {
        return Result.ok(parentPortalService.home());
    }

    @GetMapping("/children")
    @Operation(summary = "查询我的孩子")
    public Result<List<ParentChildDto>> children() {
        return Result.ok(parentPortalService.children());
    }

    @GetMapping("/notices")
    @Operation(summary = "查询家长端通知")
    public Result<List<ParentNoticeDto>> notices() {
        return Result.ok(parentPortalService.notices());
    }

    @GetMapping("/recipes")
    @Operation(summary = "查询家长端食谱")
    public Result<List<ParentRecipeDto>> recipes(@Parameter(description = "食谱日期") @RequestParam(required = false) LocalDate date) {
        return Result.ok(parentPortalService.recipes(date));
    }

    @GetMapping("/leave-requests")
    @Operation(summary = "查询我的请假申请")
    public Result<List<ParentLeaveRequestDto>> leaveRequests(
            @Parameter(description = "学生主键") @RequestParam(required = false) Long studentId) {
        return Result.ok(parentPortalService.leaveRequests(studentId));
    }

    @PostMapping("/leave-requests")
    @Operation(summary = "提交请假申请")
    public Result<Boolean> submitLeaveRequest(@RequestBody ParentLeaveRequestVo vo) {
        return Result.ok(parentPortalService.submitLeaveRequest(vo));
    }

    @GetMapping("/growth-records")
    @Operation(summary = "查询成长记录")
    public Result<List<ParentGrowthRecordDto>> growthRecords(
            @Parameter(description = "学生主键") @RequestParam(required = false) Long studentId) {
        return Result.ok(parentPortalService.growthRecords(studentId));
    }
}
