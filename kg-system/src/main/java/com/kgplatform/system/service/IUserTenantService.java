package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.UserTenantDto;
import com.kgplatform.system.domain.po.UserTenant;
import com.kgplatform.system.domain.vo.UserTenantVo;
/**
 * 用户租户关系 Service 接口
 * <p>
 * IUserTenantService Service 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

public interface IUserTenantService extends IService<UserTenant> {

    Page<UserTenantDto> selectPage(Integer current, Integer size, UserTenantVo vo);

    boolean saveUserTenant(UserTenantVo vo);

    Boolean update(UserTenantVo vo);

    boolean delete(Long id);
}
