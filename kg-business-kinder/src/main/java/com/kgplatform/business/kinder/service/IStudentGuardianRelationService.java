package com.kgplatform.business.kinder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.business.kinder.domain.dto.StudentGuardianRelationDto;
import com.kgplatform.business.kinder.domain.po.StudentGuardianRelation;
import com.kgplatform.business.kinder.domain.vo.StudentGuardianRelationVo;

/**
 * 学生家长关系 Service
 */
public interface IStudentGuardianRelationService extends IService<StudentGuardianRelation> {

    Page<StudentGuardianRelationDto> selectPage(Integer current, Integer size, StudentGuardianRelationVo vo);

    boolean saveRelation(StudentGuardianRelationVo vo);

    boolean updateRelation(StudentGuardianRelationVo vo);

    boolean deleteRelation(Long id);
}
