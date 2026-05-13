package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kgplatform.business.kinder.domain.dto.ParentAccountOpenDto;
import com.kgplatform.business.kinder.domain.po.Guardian;
import com.kgplatform.business.kinder.domain.po.Student;
import com.kgplatform.business.kinder.domain.po.StudentGuardianRelation;
import com.kgplatform.business.kinder.domain.vo.ParentAccountOpenVo;
import com.kgplatform.business.kinder.mapper.GuardianMapper;
import com.kgplatform.business.kinder.mapper.StudentGuardianRelationMapper;
import com.kgplatform.business.kinder.mapper.StudentMapper;
import com.kgplatform.business.kinder.mapper.master.MasterParentAccountMapper;
import com.kgplatform.business.kinder.service.ParentAccountService;
import com.kgplatform.common.datasource.context.TenantContextHolder;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 家长账号服务实现
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ParentAccountServiceImpl implements ParentAccountService {

    private final MasterParentAccountMapper masterParentAccountMapper;
    private final GuardianMapper guardianMapper;
    private final StudentMapper studentMapper;
    private final StudentGuardianRelationMapper relationMapper;
    private final PasswordEncoder passwordEncoder;

    public ParentAccountServiceImpl(MasterParentAccountMapper masterParentAccountMapper,
                                    GuardianMapper guardianMapper,
                                    StudentMapper studentMapper,
                                    StudentGuardianRelationMapper relationMapper,
                                    PasswordEncoder passwordEncoder) {
        this.masterParentAccountMapper = masterParentAccountMapper;
        this.guardianMapper = guardianMapper;
        this.studentMapper = studentMapper;
        this.relationMapper = relationMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ParentAccountOpenDto openAccount(ParentAccountOpenVo vo) {
        Asserts.notNull(vo, "开通家长账号参数不能为空");
        Asserts.notBlank(vo.getPhone(), "手机号不能为空");
        Asserts.notBlank(vo.getGuardianName(), "家长姓名不能为空");
        Asserts.notBlank(vo.getPassword(), "登录密码不能为空");
        Asserts.notNull(vo.getStudentId(), "学生主键不能为空");
        Asserts.notBlank(vo.getRelationType(), "关系类型不能为空");

        Long tenantId = TenantContextHolder.getTenantId();
        Asserts.notNull(tenantId, "当前租户主键不能为空");
        assertStudentExists(vo.getStudentId());

        Long userId = ensureMasterUser(vo);
        ensureUserTenant(userId, tenantId);
        ensureParentRole(userId);
        Guardian guardian = ensureGuardian(vo, userId);
        StudentGuardianRelation relation = ensureRelation(vo, guardian.getId());

        return new ParentAccountOpenDto()
                .setUserId(userId)
                .setGuardianId(guardian.getId())
                .setRelationId(relation.getId())
                .setUsername(vo.getPhone());
    }

    private Long ensureMasterUser(ParentAccountOpenVo vo) {
        Long existingUserId = masterParentAccountMapper.selectUserIdByUsername(vo.getPhone());
        if (existingUserId != null) {
            return existingUserId;
        }
        Long userId = IdWorker.getId();
        masterParentAccountMapper.insertUser(
                userId,
                vo.getPhone(),
                vo.getGuardianName(),
                vo.getPhone(),
                passwordEncoder.encode(vo.getPassword())
        );
        return userId;
    }

    private void ensureUserTenant(Long userId, Long tenantId) {
        Long existingId = masterParentAccountMapper.selectUserTenantId(userId, tenantId);
        if (existingId == null) {
            masterParentAccountMapper.insertUserTenant(IdWorker.getId(), userId, tenantId);
        }
    }

    private void ensureParentRole(Long userId) {
        Long roleId = masterParentAccountMapper.selectParentRoleId();
        Asserts.notNull(roleId, "家长角色 PARENT_PORTAL 不存在");
        Long existingId = masterParentAccountMapper.selectUserRoleId(userId, roleId);
        if (existingId == null) {
            masterParentAccountMapper.insertUserRole(IdWorker.getId(), userId, roleId);
        }
    }

    private Guardian ensureGuardian(ParentAccountOpenVo vo, Long userId) {
        Guardian existing = guardianMapper.selectOne(Wrappers.<Guardian>lambdaQuery()
                .eq(Guardian::getUserId, userId)
                .eq(Guardian::getDeleteStatus, Boolean.FALSE)
                .last("LIMIT 1"));
        if (existing != null) {
            return existing;
        }
        Guardian guardian = new Guardian()
                .setUserId(userId)
                .setGuardianName(vo.getGuardianName())
                .setPhone(vo.getPhone())
                .setStatus(1);
        guardian.setDeleteStatus(Boolean.FALSE);
        guardianMapper.insert(guardian);
        return guardian;
    }

    private StudentGuardianRelation ensureRelation(ParentAccountOpenVo vo, Long guardianId) {
        StudentGuardianRelation existing = relationMapper.selectOne(Wrappers.<StudentGuardianRelation>lambdaQuery()
                .eq(StudentGuardianRelation::getStudentId, vo.getStudentId())
                .eq(StudentGuardianRelation::getGuardianId, guardianId)
                .eq(StudentGuardianRelation::getDeleteStatus, Boolean.FALSE)
                .last("LIMIT 1"));
        if (existing != null) {
            return existing;
        }
        StudentGuardianRelation relation = new StudentGuardianRelation()
                .setStudentId(vo.getStudentId())
                .setGuardianId(guardianId)
                .setRelationType(vo.getRelationType())
                .setPrimaryContact(Boolean.TRUE.equals(vo.getPrimaryContact()) ? 1 : 0)
                .setStatus(1);
        relation.setDeleteStatus(Boolean.FALSE);
        relationMapper.insert(relation);
        return relation;
    }

    private void assertStudentExists(Long studentId) {
        Student student = studentMapper.selectById(studentId);
        Asserts.isTrue(student != null && !Boolean.TRUE.equals(student.getDeleteStatus()), "学生不存在");
    }
}
