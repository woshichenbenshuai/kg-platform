package com.kgplatform.business.kinder.web.rest;

import com.kgplatform.business.kinder.domain.dto.KindergartenClassDto;
import com.kgplatform.business.kinder.domain.dto.ParentNoticeDto;
import com.kgplatform.business.kinder.domain.dto.ParentRecipeDto;
import com.kgplatform.business.kinder.domain.dto.StudentDto;
import com.kgplatform.business.kinder.domain.po.GrowthRecord;
import com.kgplatform.business.kinder.domain.po.LeaveRequest;
import com.kgplatform.business.kinder.domain.vo.GrowthRecordVo;
import com.kgplatform.business.kinder.domain.vo.LeaveRequestManageVo;
import com.kgplatform.business.kinder.service.TeacherPortalService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 老师端门户控制层
 * <p>
 * TeacherPortalResource 控制层
 *
 * @author kg_chen
 * @since 2026-06-10 00:00:00
 */
@Validated
@RestController
@Tag(name = "TeacherPortalResource", description = "老师端门户")
@RequestMapping(path = "/teacher-portal", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeacherPortalResource {

    private final TeacherPortalService teacherPortalService;

    public TeacherPortalResource(TeacherPortalService teacherPortalService) {
        this.teacherPortalService = teacherPortalService;
    }

    @GetMapping("/home")
    @Operation(summary = "老师端首页")
    public Result<Map<String, Object>> home() {
        return Result.ok(teacherPortalService.home());
    }

    @GetMapping("/classes")
    @Operation(summary = "我的班级")
    public Result<List<KindergartenClassDto>> classes() {
        return Result.ok(teacherPortalService.classes());
    }

    @GetMapping("/students")
    @Operation(summary = "我的学生")
    public Result<List<StudentDto>> students() {
        return Result.ok(teacherPortalService.students());
    }

    @GetMapping("/notices")
    @Operation(summary = "通知列表")
    public Result<List<ParentNoticeDto>> notices() {
        return Result.ok(teacherPortalService.notices());
    }

    @GetMapping("/recipes")
    @Operation(summary = "食谱列表")
    public Result<List<ParentRecipeDto>> recipes(@Parameter(description = "日期") @RequestParam(required = false) LocalDate date) {
        return Result.ok(teacherPortalService.recipes(date));
    }

    @GetMapping("/leave-requests")
    @Operation(summary = "请假列表")
    public Result<List<LeaveRequest>> leaveRequests() {
        return Result.ok(teacherPortalService.leaveRequests());
    }

    @PutMapping("/leave-requests/approve")
    @Operation(summary = "审批请假")
    public Result<Boolean> approveLeaveRequest(@RequestBody LeaveRequestManageVo vo) {
        return Result.ok(teacherPortalService.approveLeaveRequest(vo));
    }

    @GetMapping("/growth-records")
    @Operation(summary = "成长记录列表")
    public Result<List<GrowthRecord>> growthRecords() {
        return Result.ok(teacherPortalService.growthRecords());
    }

    @PostMapping("/growth-records")
    @Operation(summary = "新增成长记录")
    public Result<Boolean> saveGrowthRecord(@RequestBody GrowthRecordVo vo) {
        return Result.ok(teacherPortalService.saveGrowthRecord(vo));
    }

    @PutMapping("/growth-records")
    @Operation(summary = "修改成长记录")
    public Result<Boolean> updateGrowthRecord(@RequestBody GrowthRecordVo vo) {
        return Result.ok(teacherPortalService.updateGrowthRecord(vo));
    }

    @DeleteMapping("/growth-records")
    @Operation(summary = "删除成长记录")
    public Result<Boolean> deleteGrowthRecord(@Parameter(description = "成长记录主键") @RequestParam Long id) {
        return Result.ok(teacherPortalService.deleteGrowthRecord(id));
    }
}
