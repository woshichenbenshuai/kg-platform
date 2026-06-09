package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kgplatform.business.kinder.domain.dto.TeacherAccountOpenDto;
import com.kgplatform.business.kinder.domain.po.Teacher;
import com.kgplatform.business.kinder.domain.vo.TeacherAccountOpenVo;
import com.kgplatform.business.kinder.mapper.TeacherMapper;
import com.kgplatform.business.kinder.mapper.master.MasterTeacherAccountMapper;
import com.kgplatform.business.kinder.service.TeacherAccountService;
import com.kgplatform.common.datasource.context.TenantContextHolder;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Teacher account provisioning service implementation.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherAccountServiceImpl implements TeacherAccountService {

    private final MasterTeacherAccountMapper masterTeacherAccountMapper;
    private final TeacherMapper teacherMapper;
    private final PasswordEncoder passwordEncoder;

    public TeacherAccountServiceImpl(MasterTeacherAccountMapper masterTeacherAccountMapper,
                                     TeacherMapper teacherMapper,
                                     PasswordEncoder passwordEncoder) {
        this.masterTeacherAccountMapper = masterTeacherAccountMapper;
        this.teacherMapper = teacherMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public TeacherAccountOpenDto openAccount(TeacherAccountOpenVo vo) {
        Asserts.notNull(vo, "Teacher account parameter is required");
        Asserts.notBlank(vo.getPhone(), "Phone is required");
        Asserts.notBlank(vo.getTeacherNo(), "Teacher number is required");
        Asserts.notBlank(vo.getTeacherName(), "Teacher name is required");
        Asserts.notBlank(vo.getPassword(), "Password is required");

        Long tenantId = TenantContextHolder.getTenantId();
        Asserts.notNull(tenantId, "Current tenant id is required");

        Long userId = ensureMasterUser(vo);
        ensureUserTenant(userId, tenantId);
        ensureTeacherRole(userId);
        Teacher teacher = ensureTeacher(vo, userId);

        return new TeacherAccountOpenDto()
                .setUserId(userId)
                .setTeacherId(teacher.getId())
                .setUsername(vo.getPhone());
    }

    private Long ensureMasterUser(TeacherAccountOpenVo vo) {
        Long existingUserId = masterTeacherAccountMapper.selectUserIdByUsername(vo.getPhone());
        if (existingUserId != null) {
            return existingUserId;
        }
        Long userId = IdWorker.getId();
        masterTeacherAccountMapper.insertUser(
                userId,
                vo.getPhone(),
                vo.getTeacherName(),
                vo.getPhone(),
                passwordEncoder.encode(vo.getPassword())
        );
        return userId;
    }

    private void ensureUserTenant(Long userId, Long tenantId) {
        Long existingId = masterTeacherAccountMapper.selectUserTenantId(userId, tenantId);
        if (existingId == null) {
            masterTeacherAccountMapper.insertUserTenant(IdWorker.getId(), userId, tenantId);
        }
    }

    private void ensureTeacherRole(Long userId) {
        Long roleId = masterTeacherAccountMapper.selectTeacherRoleId();
        Asserts.notNull(roleId, "Teacher role TEACHER_PORTAL does not exist");
        Long existingId = masterTeacherAccountMapper.selectUserRoleId(userId, roleId);
        if (existingId == null) {
            masterTeacherAccountMapper.insertUserRole(IdWorker.getId(), userId, roleId);
        }
    }

    private Teacher ensureTeacher(TeacherAccountOpenVo vo, Long userId) {
        Teacher existingByUser = teacherMapper.selectOne(Wrappers.<Teacher>lambdaQuery()
                .eq(Teacher::getUserId, userId)
                .eq(Teacher::getDeleteStatus, Boolean.FALSE)
                .last("LIMIT 1"));
        if (existingByUser != null) {
            return existingByUser;
        }

        Long teacherNoCount = teacherMapper.selectCount(Wrappers.<Teacher>lambdaQuery()
                .eq(Teacher::getTeacherNo, vo.getTeacherNo())
                .eq(Teacher::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(teacherNoCount == 0, "Teacher number already exists");

        Teacher teacher = new Teacher()
                .setUserId(userId)
                .setTeacherNo(vo.getTeacherNo())
                .setTeacherName(vo.getTeacherName())
                .setPhone(vo.getPhone())
                .setGender(vo.getGender())
                .setStatus(1);
        teacher.setDeleteStatus(Boolean.FALSE);
        teacherMapper.insert(teacher);
        return teacher;
    }
}
