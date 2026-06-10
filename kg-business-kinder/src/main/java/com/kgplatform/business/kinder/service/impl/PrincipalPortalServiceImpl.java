package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kgplatform.business.kinder.domain.po.DailyRecipe;
import com.kgplatform.business.kinder.domain.po.GrowthRecord;
import com.kgplatform.business.kinder.domain.po.Guardian;
import com.kgplatform.business.kinder.domain.po.KindergartenClass;
import com.kgplatform.business.kinder.domain.po.KindergartenNotice;
import com.kgplatform.business.kinder.domain.po.LeaveRequest;
import com.kgplatform.business.kinder.domain.po.Student;
import com.kgplatform.business.kinder.domain.po.Teacher;
import com.kgplatform.business.kinder.mapper.DailyRecipeMapper;
import com.kgplatform.business.kinder.mapper.GrowthRecordMapper;
import com.kgplatform.business.kinder.mapper.GuardianMapper;
import com.kgplatform.business.kinder.mapper.KindergartenClassMapper;
import com.kgplatform.business.kinder.mapper.KindergartenNoticeMapper;
import com.kgplatform.business.kinder.mapper.LeaveRequestMapper;
import com.kgplatform.business.kinder.mapper.StudentMapper;
import com.kgplatform.business.kinder.mapper.TeacherMapper;
import com.kgplatform.business.kinder.service.PrincipalPortalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 园长端门户服务实现
 * <p>
 * PrincipalPortalServiceImpl 服务实现
 *
 * @author kg_chen
 * @since 2026-06-10 00:00:00
 */
@Service
@Transactional(readOnly = true)
public class PrincipalPortalServiceImpl implements PrincipalPortalService {

    private final KindergartenClassMapper classMapper;
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;
    private final GuardianMapper guardianMapper;
    private final LeaveRequestMapper leaveRequestMapper;
    private final KindergartenNoticeMapper noticeMapper;
    private final DailyRecipeMapper recipeMapper;
    private final GrowthRecordMapper growthRecordMapper;

    public PrincipalPortalServiceImpl(KindergartenClassMapper classMapper,
                                      TeacherMapper teacherMapper,
                                      StudentMapper studentMapper,
                                      GuardianMapper guardianMapper,
                                      LeaveRequestMapper leaveRequestMapper,
                                      KindergartenNoticeMapper noticeMapper,
                                      DailyRecipeMapper recipeMapper,
                                      GrowthRecordMapper growthRecordMapper) {
        this.classMapper = classMapper;
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
        this.guardianMapper = guardianMapper;
        this.leaveRequestMapper = leaveRequestMapper;
        this.noticeMapper = noticeMapper;
        this.recipeMapper = recipeMapper;
        this.growthRecordMapper = growthRecordMapper;
    }

    @Override
    public Map<String, Object> home() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("classCount", countClasses());
        data.put("teacherCount", teacherMapper.selectCount(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getDeleteStatus, Boolean.FALSE)));
        data.put("studentCount", studentMapper.selectCount(Wrappers.<Student>lambdaQuery().eq(Student::getDeleteStatus, Boolean.FALSE)));
        data.put("guardianCount", guardianMapper.selectCount(Wrappers.<Guardian>lambdaQuery().eq(Guardian::getDeleteStatus, Boolean.FALSE)));
        data.put("pendingLeaveCount", leaveRequestMapper.selectCount(Wrappers.<LeaveRequest>lambdaQuery()
                .eq(LeaveRequest::getApproveStatus, "PENDING")
                .eq(LeaveRequest::getDeleteStatus, Boolean.FALSE)));
        data.put("noticeCount", noticeMapper.selectCount(Wrappers.<KindergartenNotice>lambdaQuery().eq(KindergartenNotice::getDeleteStatus, Boolean.FALSE)));
        data.put("todayRecipeCount", recipeMapper.selectCount(Wrappers.<DailyRecipe>lambdaQuery()
                .eq(DailyRecipe::getRecipeDate, LocalDate.now())
                .eq(DailyRecipe::getDeleteStatus, Boolean.FALSE)));
        data.put("growthRecordCount", growthRecordMapper.selectCount(Wrappers.<GrowthRecord>lambdaQuery().eq(GrowthRecord::getDeleteStatus, Boolean.FALSE)));
        data.put("latestNotices", noticeMapper.selectList(Wrappers.<KindergartenNotice>lambdaQuery()
                .eq(KindergartenNotice::getDeleteStatus, Boolean.FALSE)
                .orderByDesc(KindergartenNotice::getPublishTime)
                .orderByDesc(KindergartenNotice::getCreateTime)
                .last("LIMIT 5")));
        data.put("todayRecipes", recipeMapper.selectList(Wrappers.<DailyRecipe>lambdaQuery()
                .eq(DailyRecipe::getRecipeDate, LocalDate.now())
                .eq(DailyRecipe::getDeleteStatus, Boolean.FALSE)
                .orderByAsc(DailyRecipe::getMealType)));
        return data;
    }

    private Long countClasses() {
        return classMapper.selectCount(Wrappers.<KindergartenClass>lambdaQuery()
                .eq(KindergartenClass::getDeleteStatus, Boolean.FALSE));
    }
}
