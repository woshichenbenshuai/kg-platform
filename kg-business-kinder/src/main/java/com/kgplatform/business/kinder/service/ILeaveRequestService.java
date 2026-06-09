package com.kgplatform.business.kinder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.business.kinder.domain.po.LeaveRequest;
import com.kgplatform.business.kinder.domain.vo.LeaveRequestManageVo;

public interface ILeaveRequestService extends IService<LeaveRequest> {

    Page<LeaveRequest> selectPage(Integer current, Integer size, LeaveRequestManageVo vo);

    boolean approve(LeaveRequestManageVo vo);

    boolean deleteLeaveRequest(Long id);
}
