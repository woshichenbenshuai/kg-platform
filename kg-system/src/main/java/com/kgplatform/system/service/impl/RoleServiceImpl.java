package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.RoleConverter;
import com.kgplatform.system.domain.dto.RoleDto;
import com.kgplatform.system.domain.po.Role;
import com.kgplatform.system.domain.vo.RoleVo;
import com.kgplatform.system.mapper.RoleMapper;
import com.kgplatform.system.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("roleService")
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final RoleConverter roleConverter;

    public RoleServiceImpl(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDto> selectPage(Integer current, Integer size, RoleVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    public boolean saveRole(RoleVo vo) {
        Asserts.notNull(vo, "角色参数不能为空");
        Asserts.notBlank(vo.getRoleCode(), "角色编码不能为空");
        Asserts.notBlank(vo.getRoleName(), "角色名称不能为空");
        long count = baseMapper.selectCount(Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleCode, vo.getRoleCode())
                .eq(Role::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "角色编码已存在");
        return super.save(roleConverter.vo2Domain(vo));
    }

    @Override
    public Boolean update(RoleVo vo) {
        Asserts.notNull(vo, "角色参数不能为空");
        Asserts.notNull(vo.getId(), "主键不能为空");
        Role old = super.getById(vo.getId());
        Asserts.notNull(old, "角色不存在");
        long count = baseMapper.selectCount(Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleCode, vo.getRoleCode())
                .ne(Role::getId, vo.getId())
                .eq(Role::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "角色编码已存在");
        return super.updateById(roleConverter.vo2Domain(vo));
    }

    @Override
    public boolean delete(Long id) {
        Role role = new Role();
        role.setId(id);
        role.setDeleteStatus(Boolean.TRUE);
        return super.updateById(role);
    }
}
