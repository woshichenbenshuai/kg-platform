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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
        Asserts.notNull(vo, "User role payload can not be null");
        Asserts.notNull(vo.getBindUserId(), "User id can not be null");
        Asserts.notNull(vo.getBindRoleId(), "Role id can not be null");
        long count = baseMapper.selectCount(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, vo.getBindUserId())
                .eq(UserRole::getRoleId, vo.getBindRoleId())
                .eq(UserRole::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "User role relation already exists");
        return super.save(userRoleConverter.vo2Domain(vo));
    }

    @Override
    public Boolean update(UserRoleVo vo) {
        Asserts.notNull(vo, "User role payload can not be null");
        Asserts.notNull(vo.getId(), "Id can not be null");
        UserRole old = super.getById(vo.getId());
        Asserts.notNull(old, "User role relation does not exist");
        long count = baseMapper.selectCount(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, vo.getBindUserId())
                .eq(UserRole::getRoleId, vo.getBindRoleId())
                .ne(UserRole::getId, vo.getId())
                .eq(UserRole::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "User role relation already exists");
        return super.updateById(userRoleConverter.vo2Domain(vo));
    }

    @Override
    public boolean delete(Long id) {
        Asserts.notNull(id, "Id can not be null");
        UserRole userRole = new UserRole();
        userRole.setId(id);
        userRole.setDeleteStatus(Boolean.TRUE);
        return super.updateById(userRole);
    }
}
