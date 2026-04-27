package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.UserTenantDto;
import com.kgplatform.system.domain.po.UserTenant;
import com.kgplatform.system.domain.vo.UserTenantVo;

public interface IUserTenantService extends IService<UserTenant> {

    Page<UserTenantDto> selectPage(Integer current, Integer size, UserTenantVo vo);

    boolean saveUserTenant(UserTenantVo vo);

    Boolean update(UserTenantVo vo);

    boolean delete(Long id);
}
