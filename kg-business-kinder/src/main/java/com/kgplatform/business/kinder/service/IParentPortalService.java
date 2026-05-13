package com.kgplatform.business.kinder.service;

import com.kgplatform.business.kinder.domain.dto.ParentChildDto;
import com.kgplatform.business.kinder.domain.dto.ParentGrowthRecordDto;
import com.kgplatform.business.kinder.domain.dto.ParentHomeDto;
import com.kgplatform.business.kinder.domain.dto.ParentLeaveRequestDto;
import com.kgplatform.business.kinder.domain.dto.ParentNoticeDto;
import com.kgplatform.business.kinder.domain.dto.ParentRecipeDto;
import com.kgplatform.business.kinder.domain.vo.ParentLeaveRequestVo;

import java.time.LocalDate;
import java.util.List;

/**
 * 家长端 Service
 */
public interface IParentPortalService {

    ParentHomeDto home();

    List<ParentChildDto> children();

    List<ParentNoticeDto> notices();

    List<ParentRecipeDto> recipes(LocalDate date);

    List<ParentLeaveRequestDto> leaveRequests(Long studentId);

    boolean submitLeaveRequest(ParentLeaveRequestVo vo);

    List<ParentGrowthRecordDto> growthRecords(Long studentId);
}
