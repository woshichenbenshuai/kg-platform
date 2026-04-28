package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.business.kinder.domain.dto.StudentDetailDto;
import com.kgplatform.business.kinder.domain.dto.StudentDto;
import com.kgplatform.business.kinder.domain.dto.TenantDto;
import com.kgplatform.business.kinder.domain.po.Student;
import com.kgplatform.business.kinder.domain.vo.StudentVo;
import com.kgplatform.business.kinder.mapper.StudentMapper;
import com.kgplatform.business.kinder.service.IStudentService;
import com.kgplatform.business.kinder.service.TenantQueryService;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学生 Service 实现
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    private final TenantQueryService tenantQueryService;

    public StudentServiceImpl(TenantQueryService tenantQueryService) {
        this.tenantQueryService = tenantQueryService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDto> selectPage(Integer current, Integer size, StudentVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDetailDto selectDetail(Long id) {
        Asserts.notNull(id, "学生主键不能为空");

        Student student = getById(id);
        Asserts.notNull(student, "学生不存在");
        Asserts.isTrue(student.getDeleteStatus() == null || !student.getDeleteStatus(), "学生不存在");

        TenantDto tenant = tenantQueryService.getCurrentTenant();
        String currentUserNickname = tenantQueryService.getCurrentUserNickname();
        return new StudentDetailDto()
                .setStudent(toStudentDto(student))
                .setTenant(tenant)
                .setCurrentUserNickname(currentUserNickname);
    }

    @Override
    @Transactional(readOnly = true)
    public String currentDatabase() {
        return baseMapper.selectCurrentDatabase();
    }

    private StudentDto toStudentDto(Student student) {
        return new StudentDto()
                .setId(student.getId())
                .setStudentNo(student.getStudentNo())
                .setStudentName(student.getStudentName())
                .setGender(student.getGender())
                .setBirthday(student.getBirthday())
                .setStatus(student.getStatus())
                .setDeleteStatus(student.getDeleteStatus())
                .setCreateTime(student.getCreateTime());
    }
}
