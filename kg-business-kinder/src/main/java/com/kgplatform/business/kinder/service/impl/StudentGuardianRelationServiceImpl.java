package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.business.kinder.domain.dto.StudentGuardianRelationDto;
import com.kgplatform.business.kinder.domain.po.Guardian;
import com.kgplatform.business.kinder.domain.po.Student;
import com.kgplatform.business.kinder.domain.po.StudentGuardianRelation;
import com.kgplatform.business.kinder.domain.vo.StudentGuardianRelationVo;
import com.kgplatform.business.kinder.mapper.GuardianMapper;
import com.kgplatform.business.kinder.mapper.StudentGuardianRelationMapper;
import com.kgplatform.business.kinder.mapper.StudentMapper;
import com.kgplatform.business.kinder.service.IStudentGuardianRelationService;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 学生家长关系 Service 实现
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StudentGuardianRelationServiceImpl extends ServiceImpl<StudentGuardianRelationMapper, StudentGuardianRelation>
        implements IStudentGuardianRelationService {

    private final StudentMapper studentMapper;
    private final GuardianMapper guardianMapper;

    public StudentGuardianRelationServiceImpl(StudentMapper studentMapper, GuardianMapper guardianMapper) {
        this.studentMapper = studentMapper;
        this.guardianMapper = guardianMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentGuardianRelationDto> selectPage(Integer current, Integer size, StudentGuardianRelationVo vo) {
        StudentGuardianRelationVo queryVo = vo == null ? new StudentGuardianRelationVo() : vo;
        LambdaQueryWrapper<StudentGuardianRelation> query = Wrappers.<StudentGuardianRelation>lambdaQuery()
                .eq(StudentGuardianRelation::getDeleteStatus, Boolean.FALSE)
                .eq(queryVo.getStudentId() != null, StudentGuardianRelation::getStudentId, queryVo.getStudentId())
                .eq(queryVo.getGuardianId() != null, StudentGuardianRelation::getGuardianId, queryVo.getGuardianId())
                .eq(queryVo.getStatus() != null, StudentGuardianRelation::getStatus, queryVo.getStatus())
                .orderByDesc(StudentGuardianRelation::getPrimaryContact)
                .orderByDesc(StudentGuardianRelation::getCreateTime);
        Page<StudentGuardianRelation> page = page(new Page<>(current, size), query);
        Page<StudentGuardianRelationDto> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        result.setRecords(page.getRecords().stream().map(this::toDto).toList());
        return result;
    }

    @Override
    public boolean saveRelation(StudentGuardianRelationVo vo) {
        Asserts.notNull(vo, "绑定关系参数不能为空");
        validateRelation(vo);
        assertRelationAvailable(vo.getStudentId(), vo.getGuardianId(), null);
        StudentGuardianRelation entity = toEntity(vo);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        entity.setDeleteStatus(Boolean.FALSE);
        return save(entity);
    }

    @Override
    public boolean updateRelation(StudentGuardianRelationVo vo) {
        Asserts.notNull(vo, "绑定关系参数不能为空");
        Asserts.notNull(vo.getId(), "绑定关系主键不能为空");
        validateRelation(vo);
        assertRelationAvailable(vo.getStudentId(), vo.getGuardianId(), vo.getId());
        return updateById(toEntity(vo));
    }

    @Override
    public boolean deleteRelation(Long id) {
        Asserts.notNull(id, "绑定关系主键不能为空");
        StudentGuardianRelation entity = new StudentGuardianRelation();
        entity.setId(id);
        entity.setDeleteStatus(Boolean.TRUE);
        return updateById(entity);
    }

    private void validateRelation(StudentGuardianRelationVo vo) {
        Asserts.notNull(vo.getStudentId(), "学生主键不能为空");
        Asserts.notNull(vo.getGuardianId(), "家长主键不能为空");
        Asserts.notBlank(vo.getRelationType(), "关系类型不能为空");
        Student student = studentMapper.selectById(vo.getStudentId());
        Asserts.isTrue(student != null && !Boolean.TRUE.equals(student.getDeleteStatus()), "学生不存在");
        Guardian guardian = guardianMapper.selectById(vo.getGuardianId());
        Asserts.isTrue(guardian != null && !Boolean.TRUE.equals(guardian.getDeleteStatus()), "家长不存在");
    }

    private void assertRelationAvailable(Long studentId, Long guardianId, Long id) {
        long count = count(Wrappers.<StudentGuardianRelation>lambdaQuery()
                .eq(StudentGuardianRelation::getStudentId, studentId)
                .eq(StudentGuardianRelation::getGuardianId, guardianId)
                .eq(StudentGuardianRelation::getDeleteStatus, Boolean.FALSE)
                .ne(id != null, StudentGuardianRelation::getId, id));
        Asserts.isTrue(count == 0, "学生和家长已绑定");
    }

    private StudentGuardianRelation toEntity(StudentGuardianRelationVo vo) {
        StudentGuardianRelation entity = new StudentGuardianRelation()
                .setStudentId(vo.getStudentId())
                .setGuardianId(vo.getGuardianId())
                .setRelationType(vo.getRelationType())
                .setPrimaryContact(Boolean.TRUE.equals(vo.getPrimaryContact()) ? 1 : 0)
                .setStatus(vo.getStatus());
        entity.setId(vo.getId());
        return entity;
    }

    private StudentGuardianRelationDto toDto(StudentGuardianRelation entity) {
        return new StudentGuardianRelationDto()
                .setId(entity.getId())
                .setStudentId(entity.getStudentId())
                .setGuardianId(entity.getGuardianId())
                .setRelationType(entity.getRelationType())
                .setPrimaryContact(Objects.equals(entity.getPrimaryContact(), 1))
                .setStatus(entity.getStatus())
                .setDeleteStatus(entity.getDeleteStatus())
                .setCreateTime(entity.getCreateTime());
    }
}
