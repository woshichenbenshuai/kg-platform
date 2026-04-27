package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.TenantConverter;
import com.kgplatform.system.domain.dto.TenantDto;
import com.kgplatform.system.domain.po.Tenant;
import com.kgplatform.system.domain.vo.TenantVo;
import com.kgplatform.system.mapper.TenantMapper;
import com.kgplatform.system.service.ITenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("tenantService")
@Transactional(rollbackFor = Exception.class)
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    private final TenantConverter tenantConverter;

    public TenantServiceImpl(TenantConverter tenantConverter) {
        this.tenantConverter = tenantConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TenantDto> selectPage(Integer current, Integer size, TenantVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    public boolean saveTenant(TenantVo vo) {
        Asserts.notNull(vo, "租户参数不能为空");
        Asserts.notBlank(vo.getTenantCode(), "租户编码不能为空");
        Asserts.notBlank(vo.getTenantName(), "租户名称不能为空");
        long count = baseMapper.selectCount(Wrappers.<Tenant>lambdaQuery()
                .eq(Tenant::getTenantCode, vo.getTenantCode())
                .eq(Tenant::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "租户编码已存在");
        return super.save(tenantConverter.vo2Domain(vo));
    }

    @Override
    public Boolean update(TenantVo vo) {
        return super.updateById(tenantConverter.vo2Domain(vo));
    }

    @Override
    public boolean delete(Long id) {
        Tenant tenant = new Tenant();
        tenant.setId(id);
        tenant.setDeleteStatus(Boolean.TRUE);
        return super.updateById(tenant);
    }
}
