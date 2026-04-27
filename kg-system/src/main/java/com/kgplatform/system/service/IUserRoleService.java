package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.UserRoleDto;
import com.kgplatform.system.domain.po.UserRole;
import com.kgplatform.system.domain.vo.UserRoleVo;

public interface IUserRoleService extends IService<UserRole> {
    Page<UserRoleDto> selectPage(Integer current, Integer size, UserRoleVo vo);
    boolean saveUserRole(UserRoleVo vo);
    Boolean update(UserRoleVo vo);
    boolean delete(Long id);
}
