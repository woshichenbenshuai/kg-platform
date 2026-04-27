package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.RolePermissionPointDto;
import com.kgplatform.system.domain.po.RolePermissionPoint;
import com.kgplatform.system.domain.vo.RolePermissionPointVo;

public interface IRolePermissionPointService extends IService<RolePermissionPoint> {

    Page<RolePermissionPointDto> selectPage(Integer current, Integer size, RolePermissionPointVo vo);

    boolean saveRolePermissionPoint(RolePermissionPointVo vo);

    Boolean update(RolePermissionPointVo vo);

    boolean delete(Long id);
}
