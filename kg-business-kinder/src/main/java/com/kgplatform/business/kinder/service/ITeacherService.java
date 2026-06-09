package com.kgplatform.business.kinder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.business.kinder.domain.dto.TeacherDto;
import com.kgplatform.business.kinder.domain.po.Teacher;
import com.kgplatform.business.kinder.domain.vo.TeacherVo;

/**
 * Teacher service.
 */
public interface ITeacherService extends IService<Teacher> {

    Page<TeacherDto> selectPage(Integer current, Integer size, TeacherVo vo);

    boolean saveTeacher(TeacherVo vo);

    boolean updateTeacher(TeacherVo vo);

    boolean deleteTeacher(Long id);
}
