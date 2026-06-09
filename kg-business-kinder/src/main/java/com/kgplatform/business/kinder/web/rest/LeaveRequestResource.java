package com.kgplatform.business.kinder.web.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.business.kinder.domain.po.LeaveRequest;
import com.kgplatform.business.kinder.domain.vo.LeaveRequestManageVo;
import com.kgplatform.business.kinder.service.ILeaveRequestService;
import com.kgplatform.common.web.core.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@Tag(name = "LeaveRequestResource", description = "Leave request")
@RequestMapping(path = "/leave-requests", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeaveRequestResource {

    private final ILeaveRequestService leaveRequestService;

    public LeaveRequestResource(ILeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping("/pages")
    @Operation(summary = "Page leave requests")
    public Result<Page<LeaveRequest>> selectAll(
            @Parameter(description = "Current page") @RequestParam(required = false, defaultValue = "1") Integer current,
            @Parameter(description = "Page size") @RequestParam(required = false, defaultValue = "10") Integer size,
            LeaveRequestManageVo vo) {
        return Result.ok(leaveRequestService.selectPage(current, size, vo));
    }

    @PutMapping("/approve")
    @Operation(summary = "Approve leave request")
    public Result<Boolean> approve(@RequestBody LeaveRequestManageVo vo) {
        return Result.ok(leaveRequestService.approve(vo));
    }

    @DeleteMapping
    @Operation(summary = "Delete leave request")
    public Result<Boolean> delete(@RequestParam Long id) {
        return Result.ok(leaveRequestService.deleteLeaveRequest(id));
    }
}
