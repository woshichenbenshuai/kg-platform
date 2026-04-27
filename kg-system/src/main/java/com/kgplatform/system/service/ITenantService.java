package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.TenantDto;
import com.kgplatform.system.domain.po.Tenant;
import com.kgplatform.system.domain.vo.TenantVo;
/**
 * 系统租户 Service 接口
 * <p>
 * ITenantService Service 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

public interface ITenantService extends IService<Tenant> {

    Page<TenantDto> selectPage(Integer current, Integer size, TenantVo vo);

    boolean saveTenant(TenantVo vo);

    Boolean update(TenantVo vo);

    boolean delete(Long id);
}
