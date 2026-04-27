package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.UserTenantRoleConverter;
import com.kgplatform.system.domain.dto.UserTenantRoleDto;
import com.kgplatform.system.domain.po.UserTenantRole;
import com.kgplatform.system.domain.vo.UserTenantRoleVo;
import com.kgplatform.system.mapper.UserTenantRoleMapper;
import com.kgplatform.system.service.IUserTenantRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("userTenantRoleService")
@Transactional(rollbackFor = Exception.class)
public class UserTenantRoleServiceImpl extends ServiceImpl<UserTenantRoleMapper, UserTenantRole>
        implements IUserTenantRoleService {

    private final UserTenantRoleConverter userTenantRoleConverter;

    public UserTenantRoleServiceImpl(UserTenantRoleConverter userTenantRoleConverter) {
        this.userTenantRoleConverter = userTenantRoleConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserTenantRoleDto> selectPage(Integer current, Integer size, UserTenantRoleVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    public boolean saveUserTenantRole(UserTenantRoleVo vo) {
        Asserts.notNull(vo, "用户租户角色参数不能为空");
        Asserts.notNull(vo.getBindUserTenantId(), "用户租户关系主键不能为空");
        Asserts.notNull(vo.getBindRoleId(), "角色主键不能为空");
        long count = baseMapper.selectCount(Wrappers.<UserTenantRole>lambdaQuery()
                .eq(UserTenantRole::getUserTenantId, vo.getBindUserTenantId())
                .eq(UserTenantRole::getRoleId, vo.getBindRoleId())
                .eq(UserTenantRole::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "用户租户角色关系已存在");
        return super.save(userTenantRoleConverter.vo2Domain(vo));
    }

    @Override
    public Boolean update(UserTenantRoleVo vo) {
        Asserts.notNull(vo, "用户租户角色参数不能为空");
        Asserts.notNull(vo.getId(), "主键不能为空");
        UserTenantRole old = super.getById(vo.getId());
        Asserts.notNull(old, "用户租户角色关系不存在");
        long count = baseMapper.selectCount(Wrappers.<UserTenantRole>lambdaQuery()
                .eq(UserTenantRole::getUserTenantId, vo.getBindUserTenantId())
                .eq(UserTenantRole::getRoleId, vo.getBindRoleId())
                .ne(UserTenantRole::getId, vo.getId())
                .eq(UserTenantRole::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "用户租户角色关系已存在");
        return super.updateById(userTenantRoleConverter.vo2Domain(vo));
    }

    @Override
    public boolean delete(Long id) {
        Asserts.notNull(id, "主键不能为空");
        UserTenantRole userTenantRole = new UserTenantRole();
        userTenantRole.setId(id);
        userTenantRole.setDeleteStatus(Boolean.TRUE);
        return super.updateById(userTenantRole);
    }
}
