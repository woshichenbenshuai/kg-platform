package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.RolePermissionPointConverter;
import com.kgplatform.system.domain.dto.RolePermissionPointDto;
import com.kgplatform.system.domain.po.RolePermissionPoint;
import com.kgplatform.system.domain.vo.RolePermissionPointVo;
import com.kgplatform.system.mapper.RolePermissionPointMapper;
import com.kgplatform.system.service.IRolePermissionPointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("rolePermissionPointService")
@Transactional(rollbackFor = Exception.class)
public class RolePermissionPointServiceImpl
        extends ServiceImpl<RolePermissionPointMapper, RolePermissionPoint>
        implements IRolePermissionPointService {

    private final RolePermissionPointConverter rolePermissionPointConverter;

    public RolePermissionPointServiceImpl(RolePermissionPointConverter rolePermissionPointConverter) {
        this.rolePermissionPointConverter = rolePermissionPointConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RolePermissionPointDto> selectPage(Integer current, Integer size, RolePermissionPointVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    public boolean saveRolePermissionPoint(RolePermissionPointVo vo) {
        Asserts.notNull(vo, "角色权限点参数不能为空");
        Asserts.notNull(vo.getBindRoleId(), "角色主键不能为空");
        Asserts.notNull(vo.getBindPermissionPointId(), "权限点主键不能为空");
        long count = baseMapper.selectCount(Wrappers.<RolePermissionPoint>lambdaQuery()
                .eq(RolePermissionPoint::getRoleId, vo.getBindRoleId())
                .eq(RolePermissionPoint::getPermissionPointId, vo.getBindPermissionPointId())
                .eq(RolePermissionPoint::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "角色权限点关系已存在");
        return super.save(rolePermissionPointConverter.vo2Domain(vo));
    }

    @Override
    public Boolean update(RolePermissionPointVo vo) {
        Asserts.notNull(vo, "角色权限点参数不能为空");
        Asserts.notNull(vo.getId(), "角色权限点主键不能为空");
        return super.updateById(rolePermissionPointConverter.vo2Domain(vo));
    }

    @Override
    public boolean delete(Long id) {
        Asserts.notNull(id, "角色权限点主键不能为空");
        RolePermissionPoint rolePermissionPoint = new RolePermissionPoint();
        rolePermissionPoint.setId(id);
        rolePermissionPoint.setDeleteStatus(Boolean.TRUE);
        return super.updateById(rolePermissionPoint);
    }
}
