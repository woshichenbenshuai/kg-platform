package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.TenantDto;
import com.kgplatform.system.domain.po.Tenant;
import com.kgplatform.system.domain.vo.TenantVo;

public interface ITenantService extends IService<Tenant> {

    Page<TenantDto> selectPage(Integer current, Integer size, TenantVo vo);

    boolean saveTenant(TenantVo vo);

    Boolean update(TenantVo vo);

    boolean delete(Long id);
}
