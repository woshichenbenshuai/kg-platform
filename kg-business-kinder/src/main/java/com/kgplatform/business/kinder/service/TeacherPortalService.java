package com.kgplatform.business.kinder.service;

import com.kgplatform.business.kinder.domain.dto.KindergartenClassDto;
import com.kgplatform.business.kinder.domain.dto.ParentNoticeDto;
import com.kgplatform.business.kinder.domain.dto.ParentRecipeDto;
import com.kgplatform.business.kinder.domain.dto.StudentDto;
import com.kgplatform.business.kinder.domain.po.GrowthRecord;
import com.kgplatform.business.kinder.domain.po.LeaveRequest;
import com.kgplatform.business.kinder.domain.vo.GrowthRecordVo;
import com.kgplatform.business.kinder.domain.vo.LeaveRequestManageVo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 老师端门户服务
 * <p>
 * TeacherPortalService 服务接口
 *
 * @author kg_chen
 * @since 2026-06-10 00:00:00
 */
public interface TeacherPortalService {

    /**
     * 查询老师端首页概览
     *
     * @return 首页概览数据
     */
    Map<String, Object> home();

    /**
     * 查询当前老师负责班级
     *
     * @return 班级集合
     */
    List<KindergartenClassDto> classes();

    /**
     * 查询当前老师负责班级的学生
     *
     * @return 学生集合
     */
    List<StudentDto> students();

    /**
     * 查询通知
     *
     * @return 通知集合
     */
    List<ParentNoticeDto> notices();

    /**
     * 查询食谱
     *
     * @param date 日期
     * @return 食谱集合
     */
    List<ParentRecipeDto> recipes(LocalDate date);

    /**
     * 查询请假申请
     *
     * @return 请假申请集合
     */
    List<LeaveRequest> leaveRequests();

    /**
     * 审批请假申请
     *
     * @param vo 审批参数
     * @return 审批结果
     */
    boolean approveLeaveRequest(LeaveRequestManageVo vo);

    /**
     * 查询成长记录
     *
     * @return 成长记录集合
     */
    List<GrowthRecord> growthRecords();

    /**
     * 新增成长记录
     *
     * @param vo 入参
     * @return 新增结果
     */
    boolean saveGrowthRecord(GrowthRecordVo vo);

    /**
     * 修改成长记录
     *
     * @param vo 入参
     * @return 修改结果
     */
    boolean updateGrowthRecord(GrowthRecordVo vo);

    /**
     * 删除成长记录
     *
     * @param id 主键
     * @return 删除结果
     */
    boolean deleteGrowthRecord(Long id);
}
