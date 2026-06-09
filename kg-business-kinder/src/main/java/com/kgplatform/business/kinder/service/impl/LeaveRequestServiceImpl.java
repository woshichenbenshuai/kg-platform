package com.kgplatform.business.kinder.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.business.kinder.domain.po.LeaveRequest;
import com.kgplatform.business.kinder.domain.vo.LeaveRequestManageVo;
import com.kgplatform.business.kinder.mapper.LeaveRequestMapper;
import com.kgplatform.business.kinder.service.ILeaveRequestService;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class LeaveRequestServiceImpl extends ServiceImpl<LeaveRequestMapper, LeaveRequest> implements ILeaveRequestService {

    @Override
    @Transactional(readOnly = true)
    public Page<LeaveRequest> selectPage(Integer current, Integer size, LeaveRequestManageVo vo) {
        LeaveRequestManageVo queryVo = vo == null ? new LeaveRequestManageVo() : vo;
        return page(new Page<>(current, size), Wrappers.<LeaveRequest>lambdaQuery()
                .eq(LeaveRequest::getDeleteStatus, Boolean.FALSE)
                .eq(queryVo.getStudentId() != null, LeaveRequest::getStudentId, queryVo.getStudentId())
                .eq(queryVo.getGuardianId() != null, LeaveRequest::getGuardianId, queryVo.getGuardianId())
                .eq(queryVo.getApproveStatus() != null && !queryVo.getApproveStatus().isBlank(), LeaveRequest::getApproveStatus, queryVo.getApproveStatus())
                .orderByDesc(LeaveRequest::getCreateTime));
    }

    @Override
    public boolean approve(LeaveRequestManageVo vo) {
        Asserts.notNull(vo, "Leave request parameter is required");
        Asserts.notNull(vo.getId(), "Leave request id is required");
        Asserts.notBlank(vo.getApproveStatus(), "Approve status is required");
        LeaveRequest entity = new LeaveRequest()
                .setApproveStatus(vo.getApproveStatus())
                .setApproveRemark(vo.getApproveRemark());
        entity.setId(vo.getId());
        return updateById(entity);
    }

    @Override
    public boolean deleteLeaveRequest(Long id) {
        Asserts.notNull(id, "Leave request id is required");
        LeaveRequest entity = new LeaveRequest();
        entity.setId(id);
        entity.setDeleteStatus(Boolean.TRUE);
        return updateById(entity);
    }
}
