package com.kgplatform.business.kinder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.business.kinder.domain.dto.StudentDetailDto;
import com.kgplatform.business.kinder.domain.dto.StudentDto;
import com.kgplatform.business.kinder.domain.po.Student;
import com.kgplatform.business.kinder.domain.vo.StudentVo;

/**
 * 学生 Service
 */
public interface IStudentService extends IService<Student> {

    Page<StudentDto> selectPage(Integer current, Integer size, StudentVo vo);

    StudentDetailDto selectDetail(Long id);

    boolean saveStudent(StudentVo vo);

    boolean updateStudent(StudentVo vo);

    boolean deleteStudent(Long id);

    String currentDatabase();
}
