package com.kgplatform.business.kinder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.business.kinder.domain.dto.GuardianDto;
import com.kgplatform.business.kinder.domain.po.Guardian;
import com.kgplatform.business.kinder.domain.vo.GuardianVo;

/**
 * 家长 Service
 */
public interface IGuardianService extends IService<Guardian> {

    Page<GuardianDto> selectPage(Integer current, Integer size, GuardianVo vo);

    boolean saveGuardian(GuardianVo vo);

    boolean updateGuardian(GuardianVo vo);

    boolean deleteGuardian(Long id);
}
