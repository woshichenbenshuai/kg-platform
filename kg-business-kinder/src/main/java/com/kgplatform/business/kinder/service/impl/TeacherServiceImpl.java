package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.business.kinder.domain.dto.TeacherDto;
import com.kgplatform.business.kinder.domain.po.Teacher;
import com.kgplatform.business.kinder.domain.vo.TeacherVo;
import com.kgplatform.business.kinder.mapper.TeacherMapper;
import com.kgplatform.business.kinder.service.ITeacherService;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Teacher service implementation.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Override
    @Transactional(readOnly = true)
    public Page<TeacherDto> selectPage(Integer current, Integer size, TeacherVo vo) {
        TeacherVo queryVo = vo == null ? new TeacherVo() : vo;
        LambdaQueryWrapper<Teacher> query = Wrappers.<Teacher>lambdaQuery()
                .eq(Teacher::getDeleteStatus, Boolean.FALSE)
                .eq(queryVo.getStatus() != null, Teacher::getStatus, queryVo.getStatus())
                .eq(queryVo.getPlatformUserId() != null, Teacher::getUserId, queryVo.getPlatformUserId())
                .eq(queryVo.getTeacherNo() != null && !queryVo.getTeacherNo().isBlank(), Teacher::getTeacherNo, queryVo.getTeacherNo())
                .like(queryVo.getTeacherName() != null && !queryVo.getTeacherName().isBlank(), Teacher::getTeacherName, queryVo.getTeacherName())
                .like(queryVo.getPhone() != null && !queryVo.getPhone().isBlank(), Teacher::getPhone, queryVo.getPhone())
                .orderByDesc(Teacher::getCreateTime);
        Page<Teacher> page = page(new Page<>(current, size), query);
        Page<TeacherDto> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        result.setRecords(page.getRecords().stream().map(this::toDto).toList());
        return result;
    }

    @Override
    public boolean saveTeacher(TeacherVo vo) {
        Asserts.notNull(vo, "Teacher parameter is required");
        Asserts.notBlank(vo.getTeacherNo(), "Teacher number is required");
        Asserts.notBlank(vo.getTeacherName(), "Teacher name is required");
        assertTeacherNoAvailable(vo.getTeacherNo(), null);
        assertUserAvailable(vo.getPlatformUserId(), null);
        Teacher entity = toEntity(vo);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }
        entity.setDeleteStatus(Boolean.FALSE);
        return save(entity);
    }

    @Override
    public boolean updateTeacher(TeacherVo vo) {
        Asserts.notNull(vo, "Teacher parameter is required");
        Asserts.notNull(vo.getId(), "Teacher id is required");
        Asserts.notBlank(vo.getTeacherNo(), "Teacher number is required");
        Asserts.notBlank(vo.getTeacherName(), "Teacher name is required");
        assertTeacherNoAvailable(vo.getTeacherNo(), vo.getId());
        assertUserAvailable(vo.getPlatformUserId(), vo.getId());
        return updateById(toEntity(vo));
    }

    @Override
    public boolean deleteTeacher(Long id) {
        Asserts.notNull(id, "Teacher id is required");
        Teacher entity = new Teacher();
        entity.setId(id);
        entity.setDeleteStatus(Boolean.TRUE);
        return updateById(entity);
    }

    private void assertTeacherNoAvailable(String teacherNo, Long id) {
        long count = count(Wrappers.<Teacher>lambdaQuery()
                .eq(Teacher::getTeacherNo, teacherNo)
                .eq(Teacher::getDeleteStatus, Boolean.FALSE)
                .ne(id != null, Teacher::getId, id));
        Asserts.isTrue(count == 0, "Teacher number already exists");
    }

    private void assertUserAvailable(Long userId, Long id) {
        if (userId == null) {
            return;
        }
        long count = count(Wrappers.<Teacher>lambdaQuery()
                .eq(Teacher::getUserId, userId)
                .eq(Teacher::getDeleteStatus, Boolean.FALSE)
                .ne(id != null, Teacher::getId, id));
        Asserts.isTrue(count == 0, "Platform user already binds another teacher");
    }

    private Teacher toEntity(TeacherVo vo) {
        Teacher entity = new Teacher()
                .setUserId(vo.getPlatformUserId())
                .setTeacherNo(vo.getTeacherNo())
                .setTeacherName(vo.getTeacherName())
                .setPhone(vo.getPhone())
                .setGender(vo.getGender())
                .setStatus(vo.getStatus());
        entity.setId(vo.getId());
        return entity;
    }

    private TeacherDto toDto(Teacher entity) {
        return new TeacherDto()
                .setId(entity.getId())
                .setUserId(entity.getUserId())
                .setTeacherNo(entity.getTeacherNo())
                .setTeacherName(entity.getTeacherName())
                .setPhone(entity.getPhone())
                .setGender(entity.getGender())
                .setStatus(entity.getStatus())
                .setDeleteStatus(entity.getDeleteStatus())
                .setCreateTime(entity.getCreateTime());
    }
}
