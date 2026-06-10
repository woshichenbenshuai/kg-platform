package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kgplatform.business.kinder.domain.dto.KindergartenClassDto;
import com.kgplatform.business.kinder.domain.dto.ParentNoticeDto;
import com.kgplatform.business.kinder.domain.dto.ParentRecipeDto;
import com.kgplatform.business.kinder.domain.dto.StudentDto;
import com.kgplatform.business.kinder.domain.po.DailyRecipe;
import com.kgplatform.business.kinder.domain.po.GrowthRecord;
import com.kgplatform.business.kinder.domain.po.KindergartenClass;
import com.kgplatform.business.kinder.domain.po.KindergartenNotice;
import com.kgplatform.business.kinder.domain.po.LeaveRequest;
import com.kgplatform.business.kinder.domain.po.Student;
import com.kgplatform.business.kinder.domain.po.Teacher;
import com.kgplatform.business.kinder.domain.vo.GrowthRecordVo;
import com.kgplatform.business.kinder.domain.vo.LeaveRequestManageVo;
import com.kgplatform.business.kinder.mapper.DailyRecipeMapper;
import com.kgplatform.business.kinder.mapper.GrowthRecordMapper;
import com.kgplatform.business.kinder.mapper.KindergartenClassMapper;
import com.kgplatform.business.kinder.mapper.KindergartenNoticeMapper;
import com.kgplatform.business.kinder.mapper.LeaveRequestMapper;
import com.kgplatform.business.kinder.mapper.StudentMapper;
import com.kgplatform.business.kinder.mapper.TeacherMapper;
import com.kgplatform.business.kinder.service.TeacherPortalService;
import com.kgplatform.common.security.context.LoginUserContextHolder;
import com.kgplatform.common.security.model.LoginUser;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 老师端门户服务实现
 * <p>
 * TeacherPortalServiceImpl 服务实现
 *
 * @author kg_chen
 * @since 2026-06-10 00:00:00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherPortalServiceImpl implements TeacherPortalService {

    private final TeacherMapper teacherMapper;
    private final KindergartenClassMapper classMapper;
    private final StudentMapper studentMapper;
    private final KindergartenNoticeMapper noticeMapper;
    private final DailyRecipeMapper recipeMapper;
    private final LeaveRequestMapper leaveRequestMapper;
    private final GrowthRecordMapper growthRecordMapper;

    public TeacherPortalServiceImpl(TeacherMapper teacherMapper,
                                    KindergartenClassMapper classMapper,
                                    StudentMapper studentMapper,
                                    KindergartenNoticeMapper noticeMapper,
                                    DailyRecipeMapper recipeMapper,
                                    LeaveRequestMapper leaveRequestMapper,
                                    GrowthRecordMapper growthRecordMapper) {
        this.teacherMapper = teacherMapper;
        this.classMapper = classMapper;
        this.studentMapper = studentMapper;
        this.noticeMapper = noticeMapper;
        this.recipeMapper = recipeMapper;
        this.leaveRequestMapper = leaveRequestMapper;
        this.growthRecordMapper = growthRecordMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> home() {
        Teacher teacher = requireCurrentTeacher();
        List<KindergartenClass> classes = loadClasses(teacher.getId());
        List<Student> students = loadStudents(classes);
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("teacherName", teacher.getTeacherName());
        data.put("classCount", classes.size());
        data.put("studentCount", students.size());
        data.put("pendingLeaveCount", loadLeaveRequests(students).stream()
                .filter(item -> Objects.equals("PENDING", item.getApproveStatus()))
                .count());
        data.put("latestNotices", notices());
        data.put("todayRecipes", recipes(LocalDate.now()));
        data.put("recentGrowthRecords", growthRecords().stream().limit(5).toList());
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public List<KindergartenClassDto> classes() {
        return loadClasses(requireCurrentTeacher().getId()).stream().map(this::toClassDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> students() {
        return loadStudents(loadClasses(requireCurrentTeacher().getId())).stream().map(this::toStudentDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParentNoticeDto> notices() {
        return noticeMapper.selectList(Wrappers.<KindergartenNotice>lambdaQuery()
                        .eq(KindergartenNotice::getStatus, 1)
                        .eq(KindergartenNotice::getDeleteStatus, Boolean.FALSE)
                        .orderByDesc(KindergartenNotice::getPublishTime)
                        .orderByDesc(KindergartenNotice::getCreateTime)
                        .last("LIMIT 20"))
                .stream()
                .map(this::toNoticeDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<LeaveRequest> leaveRequests() {
        return loadLeaveRequests(loadStudents(loadClasses(requireCurrentTeacher().getId())));
    }

    @Override
    public boolean approveLeaveRequest(LeaveRequestManageVo vo) {
        Asserts.notNull(vo, "请假审批参数不能为空");
        Asserts.notNull(vo.getId(), "请假申请主键不能为空");
        Asserts.notBlank(vo.getApproveStatus(), "审批状态不能为空");
        LeaveRequest request = leaveRequestMapper.selectById(vo.getId());
        Asserts.notNull(request, "请假申请不存在");
        List<Long> studentIds = loadStudents(loadClasses(requireCurrentTeacher().getId())).stream().map(Student::getId).toList();
        Asserts.isTrue(studentIds.contains(request.getStudentId()), "无权处理该学生请假申请");
        LeaveRequest entity = new LeaveRequest().setApproveStatus(vo.getApproveStatus()).setApproveRemark(vo.getApproveRemark());
        entity.setId(vo.getId());
        return leaveRequestMapper.updateById(entity) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GrowthRecord> growthRecords() {
        List<Long> studentIds = loadStudents(loadClasses(requireCurrentTeacher().getId())).stream().map(Student::getId).toList();
        if (studentIds.isEmpty()) {
            return Collections.emptyList();
        }
        return growthRecordMapper.selectList(Wrappers.<GrowthRecord>lambdaQuery()
                .in(GrowthRecord::getStudentId, studentIds)
                .eq(GrowthRecord::getDeleteStatus, Boolean.FALSE)
                .orderByDesc(GrowthRecord::getRecordDate)
                .orderByDesc(GrowthRecord::getCreateTime));
    }

    @Override
    public boolean saveGrowthRecord(GrowthRecordVo vo) {
        validateGrowthRecordScope(vo);
        GrowthRecord entity = toGrowthRecord(vo);
        if (entity.getVisibleToParent() == null) {
            entity.setVisibleToParent(1);
        }
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        entity.setDeleteStatus(Boolean.FALSE);
        return growthRecordMapper.insert(entity) > 0;
    }

    @Override
    public boolean updateGrowthRecord(GrowthRecordVo vo) {
        Asserts.notNull(vo, "成长记录参数不能为空");
        Asserts.notNull(vo.getId(), "成长记录主键不能为空");
        validateGrowthRecordScope(vo);
        GrowthRecord existing = growthRecordMapper.selectById(vo.getId());
        Asserts.notNull(existing, "成长记录不存在");
        Asserts.isTrue(canAccessStudent(existing.getStudentId()), "无权修改该成长记录");
        return growthRecordMapper.updateById(toGrowthRecord(vo)) > 0;
    }

    @Override
    public boolean deleteGrowthRecord(Long id) {
        Asserts.notNull(id, "成长记录主键不能为空");
        GrowthRecord existing = growthRecordMapper.selectById(id);
        Asserts.notNull(existing, "成长记录不存在");
        Asserts.isTrue(canAccessStudent(existing.getStudentId()), "无权删除该成长记录");
        return growthRecordMapper.deleteById(id) > 0;
    }

    private Teacher requireCurrentTeacher() {
        LoginUser loginUser = LoginUserContextHolder.require();
        Teacher teacher = teacherMapper.selectOne(Wrappers.<Teacher>lambdaQuery()
                .eq(Teacher::getUserId, loginUser.getUserId())
                .eq(Teacher::getStatus, 1)
                .eq(Teacher::getDeleteStatus, Boolean.FALSE)
                .last("LIMIT 1"));
        Asserts.notNull(teacher, "当前用户未绑定老师信息");
        return teacher;
    }

    private List<KindergartenClass> loadClasses(Long teacherId) {
        return classMapper.selectList(Wrappers.<KindergartenClass>lambdaQuery()
                .eq(KindergartenClass::getHeadTeacherId, teacherId)
                .eq(KindergartenClass::getStatus, 1)
                .eq(KindergartenClass::getDeleteStatus, Boolean.FALSE)
                .orderByAsc(KindergartenClass::getGradeName)
                .orderByAsc(KindergartenClass::getClassName));
    }

    private List<Student> loadStudents(List<KindergartenClass> classes) {
        List<Long> classIds = classes.stream().map(KindergartenClass::getId).toList();
        if (classIds.isEmpty()) {
            return Collections.emptyList();
        }
        return studentMapper.selectList(Wrappers.<Student>lambdaQuery()
                .in(Student::getClassId, classIds)
                .eq(Student::getDeleteStatus, Boolean.FALSE)
                .orderByAsc(Student::getStudentNo));
    }

    private List<LeaveRequest> loadLeaveRequests(List<Student> students) {
        List<Long> studentIds = students.stream().map(Student::getId).toList();
        if (studentIds.isEmpty()) {
            return Collections.emptyList();
        }
        return leaveRequestMapper.selectList(Wrappers.<LeaveRequest>lambdaQuery()
                .in(LeaveRequest::getStudentId, studentIds)
                .eq(LeaveRequest::getDeleteStatus, Boolean.FALSE)
                .orderByDesc(LeaveRequest::getCreateTime));
    }

    private boolean canAccessStudent(Long studentId) {
        return loadStudents(loadClasses(requireCurrentTeacher().getId())).stream()
                .map(Student::getId)
                .anyMatch(id -> Objects.equals(id, studentId));
    }

    private void validateGrowthRecordScope(GrowthRecordVo vo) {
        Asserts.notNull(vo, "成长记录参数不能为空");
        Asserts.notNull(vo.getStudentId(), "学生主键不能为空");
        Asserts.notBlank(vo.getTitle(), "标题不能为空");
        Asserts.notBlank(vo.getContent(), "内容不能为空");
        Asserts.notNull(vo.getRecordDate(), "记录日期不能为空");
        Asserts.isTrue(canAccessStudent(vo.getStudentId()), "无权维护该学生成长记录");
    }

    private GrowthRecord toGrowthRecord(GrowthRecordVo vo) {
        GrowthRecord entity = new GrowthRecord()
                .setStudentId(vo.getStudentId())
                .setTitle(vo.getTitle())
                .setContent(vo.getContent())
                .setRecordDate(vo.getRecordDate())
                .setImageUrls(vo.getImageUrls())
                .setVisibleToParent(vo.getVisibleToParent())
                .setStatus(vo.getStatus());
        entity.setId(vo.getId());
        return entity;
    }

    private KindergartenClassDto toClassDto(KindergartenClass entity) {
        return new KindergartenClassDto()
                .setId(entity.getId())
                .setClassCode(entity.getClassCode())
                .setClassName(entity.getClassName())
                .setGradeName(entity.getGradeName())
                .setHeadTeacherId(entity.getHeadTeacherId())
                .setStatus(entity.getStatus())
                .setDeleteStatus(entity.getDeleteStatus())
                .setCreateTime(entity.getCreateTime());
    }

    private StudentDto toStudentDto(Student entity) {
        return new StudentDto()
                .setId(entity.getId())
                .setClassId(entity.getClassId())
                .setStudentNo(entity.getStudentNo())
                .setStudentName(entity.getStudentName())
                .setGender(entity.getGender())
                .setBirthday(entity.getBirthday())
                .setStatus(entity.getStatus())
                .setDeleteStatus(entity.getDeleteStatus())
                .setCreateTime(entity.getCreateTime());
    }

    private ParentNoticeDto toNoticeDto(KindergartenNotice notice) {
        return new ParentNoticeDto().setId(notice.getId()).setTitle(notice.getTitle()).setContent(notice.getContent()).setPublishTime(notice.getPublishTime());
    }

    private ParentRecipeDto toRecipeDto(DailyRecipe recipe) {
        return new ParentRecipeDto().setId(recipe.getId()).setRecipeDate(recipe.getRecipeDate()).setMealType(recipe.getMealType()).setContent(recipe.getContent());
    }
}
