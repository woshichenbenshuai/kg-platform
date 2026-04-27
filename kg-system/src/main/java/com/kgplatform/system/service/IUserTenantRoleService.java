package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.UserTenantRoleDto;
import com.kgplatform.system.domain.po.UserTenantRole;
import com.kgplatform.system.domain.vo.UserTenantRoleVo;

public interface IUserTenantRoleService extends IService<UserTenantRole> {

    Page<UserTenantRoleDto> selectPage(Integer current, Integer size, UserTenantRoleVo vo);

    boolean saveUserTenantRole(UserTenantRoleVo vo);

    Boolean update(UserTenantRoleVo vo);

    boolean delete(Long id);
}
