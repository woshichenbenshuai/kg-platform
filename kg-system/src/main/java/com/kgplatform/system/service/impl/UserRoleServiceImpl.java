package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.UserRoleConverter;
import com.kgplatform.system.domain.dto.UserRoleDto;
import com.kgplatform.system.domain.po.UserRole;
import com.kgplatform.system.domain.vo.UserRoleVo;
import com.kgplatform.system.mapper.UserRoleMapper;
import com.kgplatform.system.service.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户角色关系 Service 实现类
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
 */
@Service("userRoleService")
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    private final UserRoleConverter userRoleConverter;

    public UserRoleServiceImpl(UserRoleConverter userRoleConverter) {
        this.userRoleConverter = userRoleConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserRoleDto> selectPage(Integer current, Integer size, UserRoleVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    public boolean saveUserRole(UserRoleVo vo) {
        Asserts.notNull(vo, "用户角色参数不能为空");
        Asserts.notNull(vo.getBindUserId(), "用户主键不能为空");
        Asserts.notNull(vo.getBindRoleId(), "角色主键不能为空");
        long count = baseMapper.selectCount(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, vo.getBindUserId())
                .eq(UserRole::getRoleId, vo.getBindRoleId())
                .eq(UserRole::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "用户角色关系已存在");
        return super.save(userRoleConverter.vo2Domain(vo));
    }

    @Override
    public Boolean update(UserRoleVo vo) {
        Asserts.notNull(vo, "用户角色参数不能为空");
        Asserts.notNull(vo.getId(), "主键不能为空");
        UserRole old = super.getById(vo.getId());
        Asserts.notNull(old, "用户角色关系不存在");
        long count = baseMapper.selectCount(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, vo.getBindUserId())
                .eq(UserRole::getRoleId, vo.getBindRoleId())
                .ne(UserRole::getId, vo.getId())
                .eq(UserRole::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "用户角色关系已存在");
        return super.updateById(userRoleConverter.vo2Domain(vo));
    }

    @Override
    public boolean delete(Long id) {
        Asserts.notNull(id, "主键不能为空");
        UserRole userRole = new UserRole();
        userRole.setId(id);
        userRole.setDeleteStatus(Boolean.TRUE);
        return super.updateById(userRole);
    }
}
