package com.kgplatform.business.kinder.service;

import com.kgplatform.business.kinder.domain.dto.ParentAccountOpenDto;
import com.kgplatform.business.kinder.domain.vo.ParentAccountOpenVo;

/**
 * 家长账号服务
 */
public interface ParentAccountService {

    ParentAccountOpenDto openAccount(ParentAccountOpenVo vo);
}
