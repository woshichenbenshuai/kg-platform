package com.kgplatform.business.kinder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.business.kinder.domain.dto.StudentDto;
import com.kgplatform.business.kinder.domain.po.Student;
import com.kgplatform.business.kinder.domain.vo.StudentVo;
import org.apache.ibatis.annotations.Param;

/**
 * 学生 Mapper
 */
public interface StudentMapper extends BaseMapper<Student> {

    Page<StudentDto> selectPageList(Page<StudentDto> page, @Param("vo") StudentVo vo);

    String selectCurrentDatabase();
}
