package com.kgplatform.business.kinder.service;

import com.kgplatform.business.kinder.domain.dto.TeacherAccountOpenDto;
import com.kgplatform.business.kinder.domain.vo.TeacherAccountOpenVo;

/**
 * Teacher account provisioning service.
 */
public interface TeacherAccountService {

    TeacherAccountOpenDto openAccount(TeacherAccountOpenVo vo);
}
