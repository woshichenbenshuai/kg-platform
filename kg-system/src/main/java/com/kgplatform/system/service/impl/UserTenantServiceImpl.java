package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.UserTenantConverter;
import com.kgplatform.system.domain.dto.UserTenantDto;
import com.kgplatform.system.domain.po.UserTenant;
import com.kgplatform.system.domain.vo.UserTenantVo;
import com.kgplatform.system.mapper.UserTenantMapper;
import com.kgplatform.system.service.IUserTenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * ç¨æ·ç§æ·å³ç³» Service å®ç°ç±»
 * <p>
 * UserTenantServiceImpl Service å®ç°ç±»
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Service("userTenantService")
@Transactional(rollbackFor = Exception.class)
public class UserTenantServiceImpl extends ServiceImpl<UserTenantMapper, UserTenant> implements IUserTenantService {

    private final UserTenantConverter userTenantConverter;

    public UserTenantServiceImpl(UserTenantConverter userTenantConverter) {
        this.userTenantConverter = userTenantConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserTenantDto> selectPage(Integer current, Integer size, UserTenantVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    public boolean saveUserTenant(UserTenantVo vo) {
        Asserts.notNull(vo, "用户租户关系参数不能为空");
        Asserts.notNull(vo.getBindUserId(), "用户ID不能为空");
        Asserts.notNull(vo.getBindTenantId(), "租户ID不能为空");
        Asserts.notBlank(vo.getIdentityType(), "身份类型不能为空");
        long count = baseMapper.selectCount(Wrappers.<UserTenant>lambdaQuery()
                .eq(UserTenant::getUserId, vo.getBindUserId())
                .eq(UserTenant::getTenantId, vo.getBindTenantId())
                .eq(UserTenant::getIdentityType, vo.getIdentityType())
                .eq(UserTenant::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "用户租户关系已存在");
        return super.save(userTenantConverter.vo2Domain(vo));
    }

    @Override
    public Boolean update(UserTenantVo vo) {
        return super.updateById(userTenantConverter.vo2Domain(vo));
    }

    @Override
    public boolean delete(Long id) {
        UserTenant userTenant = new UserTenant();
        userTenant.setId(id);
        userTenant.setDeleteStatus(Boolean.TRUE);
        return super.updateById(userTenant);
    }
}
