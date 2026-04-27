package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.PermissionPointDto;
import com.kgplatform.system.domain.po.PermissionPoint;
import com.kgplatform.system.domain.vo.PermissionPointVo;

public interface IPermissionPointService extends IService<PermissionPoint> {

    Page<PermissionPointDto> selectPage(Integer current, Integer size, PermissionPointVo vo);

    boolean savePermissionPoint(PermissionPointVo vo);

    Boolean update(PermissionPointVo vo);

    boolean delete(Long id);
}
