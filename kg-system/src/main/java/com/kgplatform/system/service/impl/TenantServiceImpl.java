package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.ApiException;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.TenantConverter;
import com.kgplatform.system.domain.dto.TenantDto;
import com.kgplatform.system.domain.po.Tenant;
import com.kgplatform.system.domain.po.TenantDbConfig;
import com.kgplatform.system.domain.vo.TenantVo;
import com.kgplatform.system.mapper.TenantMapper;
import com.kgplatform.system.service.ITenantDbConfigService;
import com.kgplatform.system.service.ITenantService;
import com.kgplatform.system.service.TenantDatabaseProvisioner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;
/**
 * 系统租户 Service 实现类
 * <p>
 * TenantServiceImpl Service 实现类
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Service("tenantService")
@Transactional(rollbackFor = Exception.class)
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    private final TenantConverter tenantConverter;
    private final TenantDatabaseProvisioner tenantDatabaseProvisioner;
    private final ITenantDbConfigService tenantDbConfigService;

    public TenantServiceImpl(TenantConverter tenantConverter,
                             TenantDatabaseProvisioner tenantDatabaseProvisioner,
                             ITenantDbConfigService tenantDbConfigService) {
        this.tenantConverter = tenantConverter;
        this.tenantDatabaseProvisioner = tenantDatabaseProvisioner;
        this.tenantDbConfigService = tenantDbConfigService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TenantDto> selectPage(Integer current, Integer size, TenantVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    public boolean saveTenant(TenantVo vo) {
        Asserts.notNull(vo, "租户参数不能为空");
        if (vo.getTenantCode() == null || vo.getTenantCode().isBlank()) {
            vo.setTenantCode(generateTenantCode());
        }
        Asserts.notBlank(vo.getTenantName(), "租户名称不能为空");
        long count = baseMapper.selectCount(Wrappers.<Tenant>lambdaQuery()
                .eq(Tenant::getTenantCode, vo.getTenantCode())
                .eq(Tenant::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "幼儿园编号已存在");
        Tenant tenant = tenantConverter.vo2Domain(vo);
        return super.save(tenant);
    }

    @Override
    public String rebuildTenantDatabase(Long id) {
        Asserts.notNull(id, "租户主键不能为空");
        Tenant tenant = getById(id);
        Asserts.notNull(tenant, "租户不存在");
        Asserts.isTrue(Boolean.FALSE.equals(tenant.getDeleteStatus()), "租户不存在");
        return tenantDatabaseProvisioner.rebuild(tenant);
    }

    @Override
    public Boolean update(TenantVo vo) {
        return super.updateById(tenantConverter.vo2Domain(vo));
    }

    @Override
    public boolean delete(Long id) {
        Asserts.notNull(id, "租户主键不能为空");
        long dbConfigCount = tenantDbConfigService.count(Wrappers.<TenantDbConfig>lambdaQuery()
                .eq(TenantDbConfig::getTenantId, id)
                .eq(TenantDbConfig::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(dbConfigCount == 0, "幼儿园数据库已创建，不能删除幼儿园");
        Tenant tenant = new Tenant();
        tenant.setId(id);
        tenant.setDeleteStatus(Boolean.TRUE);
        return super.updateById(tenant);
    }

    private String generateTenantCode() {
        for (int i = 0; i < 20; i++) {
            String code = "KINDER_" + String.format("%08d", ThreadLocalRandom.current().nextInt(1, 100000000));
            long count = baseMapper.selectCount(Wrappers.<Tenant>lambdaQuery()
                    .eq(Tenant::getTenantCode, code)
                    .eq(Tenant::getDeleteStatus, Boolean.FALSE));
            if (count == 0) {
                return code;
            }
        }
        throw new ApiException("生成幼儿园编号失败，请重试");
    }
}
