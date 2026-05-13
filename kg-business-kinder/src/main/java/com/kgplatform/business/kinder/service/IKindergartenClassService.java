package com.kgplatform.business.kinder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.business.kinder.domain.dto.KindergartenClassDto;
import com.kgplatform.business.kinder.domain.po.KindergartenClass;
import com.kgplatform.business.kinder.domain.vo.KindergartenClassVo;

/**
 * 班级 Service
 */
public interface IKindergartenClassService extends IService<KindergartenClass> {

    Page<KindergartenClassDto> selectPage(Integer current, Integer size, KindergartenClassVo vo);

    boolean saveClass(KindergartenClassVo vo);

    boolean updateClass(KindergartenClassVo vo);

    boolean deleteClass(Long id);
}
