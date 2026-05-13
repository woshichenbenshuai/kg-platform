package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kgplatform.business.kinder.domain.dto.ParentChildDto;
import com.kgplatform.business.kinder.domain.dto.ParentGrowthRecordDto;
import com.kgplatform.business.kinder.domain.dto.ParentHomeDto;
import com.kgplatform.business.kinder.domain.dto.ParentLeaveRequestDto;
import com.kgplatform.business.kinder.domain.dto.ParentNoticeDto;
import com.kgplatform.business.kinder.domain.dto.ParentRecipeDto;
import com.kgplatform.business.kinder.domain.po.DailyRecipe;
import com.kgplatform.business.kinder.domain.po.GrowthRecord;
import com.kgplatform.business.kinder.domain.po.Guardian;
import com.kgplatform.business.kinder.domain.po.KindergartenClass;
import com.kgplatform.business.kinder.domain.po.KindergartenNotice;
import com.kgplatform.business.kinder.domain.po.LeaveRequest;
import com.kgplatform.business.kinder.domain.po.Student;
import com.kgplatform.business.kinder.domain.po.StudentGuardianRelation;
import com.kgplatform.business.kinder.domain.vo.ParentLeaveRequestVo;
import com.kgplatform.business.kinder.mapper.DailyRecipeMapper;
import com.kgplatform.business.kinder.mapper.GrowthRecordMapper;
import com.kgplatform.business.kinder.mapper.GuardianMapper;
import com.kgplatform.business.kinder.mapper.KindergartenClassMapper;
import com.kgplatform.business.kinder.mapper.KindergartenNoticeMapper;
import com.kgplatform.business.kinder.mapper.LeaveRequestMapper;
import com.kgplatform.business.kinder.mapper.StudentGuardianRelationMapper;
import com.kgplatform.business.kinder.mapper.StudentMapper;
import com.kgplatform.business.kinder.service.IParentPortalService;
import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 家长端 Service 实现
 */
@Service
@Transactional(readOnly = true)
public class ParentPortalServiceImpl implements IParentPortalService {

    private final GuardianMapper guardianMapper;
    private final StudentGuardianRelationMapper relationMapper;
    private final StudentMapper studentMapper;
    private final KindergartenClassMapper classMapper;
    private final KindergartenNoticeMapper noticeMapper;
    private final DailyRecipeMapper recipeMapper;
    private final LeaveRequestMapper leaveRequestMapper;
    private final GrowthRecordMapper growthRecordMapper;

    public ParentPortalServiceImpl(GuardianMapper guardianMapper,
                                   StudentGuardianRelationMapper relationMapper,
                                   StudentMapper studentMapper,
                                   KindergartenClassMapper classMapper,
                                   KindergartenNoticeMapper noticeMapper,
                                   DailyRecipeMapper recipeMapper,
                                   LeaveRequestMapper leaveRequestMapper,
                                   GrowthRecordMapper growthRecordMapper) {
        this.guardianMapper = guardianMapper;
        this.relationMapper = relationMapper;
        this.studentMapper = studentMapper;
        this.classMapper = classMapper;
        this.noticeMapper = noticeMapper;
        this.recipeMapper = recipeMapper;
        this.leaveRequestMapper = leaveRequestMapper;
        this.growthRecordMapper = growthRecordMapper;
    }

    @Override
    public ParentHomeDto home() {
        LoginUser loginUser = LoginUserContextHolder.require();
        Guardian guardian = findCurrentGuardian(loginUser);
        List<ParentChildDto> children = guardian == null ? Collections.emptyList() : loadChildren(guardian.getId());
        String parentName = guardian == null ? defaultName(loginUser) : guardian.getGuardianName();
        return new ParentHomeDto()
                .setParentName(parentName)
                .setBindStatus(!children.isEmpty())
                .setChildCount(children.size())
                .setChildren(children)
                .setNotices(notices())
                .setTodayRecipes(recipes(LocalDate.now()));
    }

    @Override
    public List<ParentChildDto> children() {
        LoginUser loginUser = LoginUserContextHolder.require();
        Guardian guardian = findCurrentGuardian(loginUser);
        return guardian == null ? Collections.emptyList() : loadChildren(guardian.getId());
    }

    @Override
    public List<ParentNoticeDto> notices() {
        return noticeMapper.selectList(Wrappers.<KindergartenNotice>lambdaQuery()
                        .eq(KindergartenNotice::getStatus, 1)
                        .eq(KindergartenNotice::getDeleteStatus, Boolean.FALSE)
                        .orderByDesc(KindergartenNotice::getPublishTime)
                        .orderByDesc(KindergartenNotice::getCreateTime)
                        .last("LIMIT 10"))
                .stream()
                .map(this::toNoticeDto)
                .toList();
    }

    @Override
    public List<ParentRecipeDto> recipes(LocalDate date) {
        LocalDate queryDate = date == null ? LocalDate.now() : date;
        return recipeMapper.selectList(Wrappers.<DailyRecipe>lambdaQuery()
                        .eq(DailyRecipe::getRecipeDate, queryDate)
                        .eq(DailyRecipe::getStatus, 1)
                        .eq(DailyRecipe::getDeleteStatus, Boolean.FALSE)
                        .orderByAsc(DailyRecipe::getMealType))
                .stream()
                .map(this::toRecipeDto)
                .toList();
    }

    @Override
    public List<ParentLeaveRequestDto> leaveRequests(Long studentId) {
        Guardian guardian = requireCurrentGuardian();
        List<Long> childIds = loadChildIds(guardian.getId());
        if (childIds.isEmpty()) {
            return Collections.emptyList();
        }
        if (studentId != null) {
            Asserts.isTrue(childIds.contains(studentId), "无权查看该学生请假记录");
        }
        List<LeaveRequest> requests = leaveRequestMapper.selectList(Wrappers.<LeaveRequest>lambdaQuery()
                .eq(LeaveRequest::getGuardianId, guardian.getId())
                .eq(studentId != null, LeaveRequest::getStudentId, studentId)
                .in(studentId == null, LeaveRequest::getStudentId, childIds)
                .eq(LeaveRequest::getDeleteStatus, Boolean.FALSE)
                .orderByDesc(LeaveRequest::getCreateTime));
        Map<Long, Student> studentMap = loadStudentMap(requests.stream().map(LeaveRequest::getStudentId).distinct().toList());
        return requests.stream().map(request -> toLeaveDto(request, studentMap.get(request.getStudentId()))).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean submitLeaveRequest(ParentLeaveRequestVo vo) {
        Asserts.notNull(vo, "请假参数不能为空");
        Asserts.notNull(vo.getStudentId(), "学生主键不能为空");
        Asserts.notNull(vo.getStartDate(), "开始日期不能为空");
        Asserts.notNull(vo.getEndDate(), "结束日期不能为空");
        Asserts.notBlank(vo.getReason(), "请假原因不能为空");
        Asserts.isTrue(!vo.getEndDate().isBefore(vo.getStartDate()), "结束日期不能早于开始日期");
        Guardian guardian = requireCurrentGuardian();
        Asserts.isTrue(loadChildIds(guardian.getId()).contains(vo.getStudentId()), "无权给该学生请假");
        LeaveRequest request = new LeaveRequest()
                .setStudentId(vo.getStudentId())
                .setGuardianId(guardian.getId())
                .setStartDate(vo.getStartDate())
                .setEndDate(vo.getEndDate())
                .setReason(vo.getReason())
                .setApproveStatus("PENDING")
                .setStatus(1);
        request.setDeleteStatus(Boolean.FALSE);
        return leaveRequestMapper.insert(request) > 0;
    }

    @Override
    public List<ParentGrowthRecordDto> growthRecords(Long studentId) {
        Guardian guardian = requireCurrentGuardian();
        List<Long> childIds = loadChildIds(guardian.getId());
        if (childIds.isEmpty()) {
            return Collections.emptyList();
        }
        if (studentId != null) {
            Asserts.isTrue(childIds.contains(studentId), "无权查看该学生成长记录");
        }
        List<GrowthRecord> records = growthRecordMapper.selectList(Wrappers.<GrowthRecord>lambdaQuery()
                .eq(studentId != null, GrowthRecord::getStudentId, studentId)
                .in(studentId == null, GrowthRecord::getStudentId, childIds)
                .eq(GrowthRecord::getVisibleToParent, 1)
                .eq(GrowthRecord::getStatus, 1)
                .eq(GrowthRecord::getDeleteStatus, Boolean.FALSE)
                .orderByDesc(GrowthRecord::getRecordDate)
                .orderByDesc(GrowthRecord::getCreateTime));
        Map<Long, Student> studentMap = loadStudentMap(records.stream().map(GrowthRecord::getStudentId).distinct().toList());
        return records.stream().map(record -> toGrowthDto(record, studentMap.get(record.getStudentId()))).toList();
    }

    private Guardian findCurrentGuardian(LoginUser loginUser) {
        Asserts.notNull(loginUser.getUserId(), "当前用户主键不能为空");
        return guardianMapper.selectOne(Wrappers.<Guardian>lambdaQuery()
                .eq(Guardian::getUserId, loginUser.getUserId())
                .eq(Guardian::getStatus, 1)
                .eq(Guardian::getDeleteStatus, Boolean.FALSE)
                .last("LIMIT 1"));
    }

    private Guardian requireCurrentGuardian() {
        Guardian guardian = findCurrentGuardian(LoginUserContextHolder.require());
        Asserts.notNull(guardian, "当前用户未绑定家长信息");
        return guardian;
    }

    private List<Long> loadChildIds(Long guardianId) {
        return relationMapper.selectList(Wrappers.<StudentGuardianRelation>lambdaQuery()
                        .eq(StudentGuardianRelation::getGuardianId, guardianId)
                        .eq(StudentGuardianRelation::getStatus, 1)
                        .eq(StudentGuardianRelation::getDeleteStatus, Boolean.FALSE))
                .stream()
                .map(StudentGuardianRelation::getStudentId)
                .distinct()
                .toList();
    }

    private List<ParentChildDto> loadChildren(Long guardianId) {
        List<StudentGuardianRelation> relations = relationMapper.selectList(Wrappers.<StudentGuardianRelation>lambdaQuery()
                .eq(StudentGuardianRelation::getGuardianId, guardianId)
                .eq(StudentGuardianRelation::getStatus, 1)
                .eq(StudentGuardianRelation::getDeleteStatus, Boolean.FALSE));
        if (relations.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, StudentGuardianRelation> relationMap = relations.stream()
                .collect(Collectors.toMap(StudentGuardianRelation::getStudentId, Function.identity(), (left, right) -> left));
        List<Student> students = studentMapper.selectBatchIds(relationMap.keySet()).stream()
                .filter(student -> student.getDeleteStatus() == null || !student.getDeleteStatus())
                .filter(student -> Objects.equals(student.getStatus(), 1))
                .toList();
        if (students.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, KindergartenClass> classMap = loadClassMap(students);
        return students.stream()
                .map(student -> toChildDto(student, relationMap.get(student.getId()), classMap.get(student.getClassId())))
                .toList();
    }

    private Map<Long, KindergartenClass> loadClassMap(List<Student> students) {
        List<Long> classIds = students.stream()
                .map(Student::getClassId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        if (classIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return classMapper.selectBatchIds(classIds).stream()
                .filter(clazz -> clazz.getDeleteStatus() == null || !clazz.getDeleteStatus())
                .collect(Collectors.toMap(KindergartenClass::getId, Function.identity(), (left, right) -> left));
    }

    private ParentChildDto toChildDto(Student student, StudentGuardianRelation relation, KindergartenClass clazz) {
        return new ParentChildDto()
                .setId(student.getId())
                .setStudentNo(student.getStudentNo())
                .setStudentName(student.getStudentName())
                .setGender(student.getGender())
                .setBirthday(student.getBirthday())
                .setClassId(student.getClassId())
                .setClassName(clazz == null ? null : clazz.getClassName())
                .setGradeName(clazz == null ? null : clazz.getGradeName())
                .setRelationType(relation == null ? null : relation.getRelationType())
                .setPrimaryContact(relation != null && Objects.equals(relation.getPrimaryContact(), 1));
    }

    private Map<Long, Student> loadStudentMap(List<Long> studentIds) {
        if (studentIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return studentMapper.selectBatchIds(studentIds).stream()
                .collect(Collectors.toMap(Student::getId, Function.identity(), (left, right) -> left));
    }

    private ParentNoticeDto toNoticeDto(KindergartenNotice notice) {
        return new ParentNoticeDto()
                .setId(notice.getId())
                .setTitle(notice.getTitle())
                .setContent(notice.getContent())
                .setPublishTime(notice.getPublishTime());
    }

    private ParentRecipeDto toRecipeDto(DailyRecipe recipe) {
        return new ParentRecipeDto()
                .setId(recipe.getId())
                .setRecipeDate(recipe.getRecipeDate())
                .setMealType(recipe.getMealType())
                .setContent(recipe.getContent());
    }

    private ParentLeaveRequestDto toLeaveDto(LeaveRequest request, Student student) {
        return new ParentLeaveRequestDto()
                .setId(request.getId())
                .setStudentId(request.getStudentId())
                .setStudentName(student == null ? null : student.getStudentName())
                .setStartDate(request.getStartDate())
                .setEndDate(request.getEndDate())
                .setReason(request.getReason())
                .setApproveStatus(request.getApproveStatus())
                .setApproveRemark(request.getApproveRemark())
                .setCreateTime(request.getCreateTime());
    }

    private ParentGrowthRecordDto toGrowthDto(GrowthRecord record, Student student) {
        return new ParentGrowthRecordDto()
                .setId(record.getId())
                .setStudentId(record.getStudentId())
                .setStudentName(student == null ? null : student.getStudentName())
                .setTitle(record.getTitle())
                .setContent(record.getContent())
                .setRecordDate(record.getRecordDate())
                .setImageUrls(record.getImageUrls());
    }

    private String defaultName(LoginUser loginUser) {
        if (loginUser.getNickname() != null && !loginUser.getNickname().isBlank()) {
            return loginUser.getNickname();
        }
        return loginUser.getUsername();
    }
}
