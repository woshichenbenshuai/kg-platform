package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.PermissionPointConverter;
import com.kgplatform.system.domain.dto.PermissionPointDto;
import com.kgplatform.system.domain.po.PermissionPoint;
import com.kgplatform.system.domain.vo.PermissionPointVo;
import com.kgplatform.system.mapper.PermissionPointMapper;
import com.kgplatform.system.service.IPermissionPointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("permissionPointService")
@Transactional(rollbackFor = Exception.class)
public class PermissionPointServiceImpl extends ServiceImpl<PermissionPointMapper, PermissionPoint>
        implements IPermissionPointService {

    private final PermissionPointConverter permissionPointConverter;

    public PermissionPointServiceImpl(PermissionPointConverter permissionPointConverter) {
        this.permissionPointConverter = permissionPointConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionPointDto> selectPage(Integer current, Integer size, PermissionPointVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    public boolean savePermissionPoint(PermissionPointVo vo) {
        Asserts.notNull(vo, "权限点参数不能为空");
        Asserts.notBlank(vo.getPermissionCode(), "权限编码不能为空");
        Asserts.notBlank(vo.getPermissionName(), "权限名称不能为空");
        Asserts.notBlank(vo.getPermissionType(), "权限类型不能为空");
        Asserts.notBlank(vo.getPermissionScope(), "权限范围不能为空");
        long count = baseMapper.selectCount(Wrappers.<PermissionPoint>lambdaQuery()
                .eq(PermissionPoint::getPermissionCode, vo.getPermissionCode())
                .eq(PermissionPoint::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "权限编码已存在");
        return super.save(permissionPointConverter.vo2Domain(vo));
    }

    @Override
    public Boolean update(PermissionPointVo vo) {
        Asserts.notNull(vo, "权限点参数不能为空");
        Asserts.notNull(vo.getId(), "权限点主键不能为空");
        return super.updateById(permissionPointConverter.vo2Domain(vo));
    }

    @Override
    public boolean delete(Long id) {
        Asserts.notNull(id, "权限点主键不能为空");
        PermissionPoint permissionPoint = new PermissionPoint();
        permissionPoint.setId(id);
        permissionPoint.setDeleteStatus(Boolean.TRUE);
        return super.updateById(permissionPoint);
    }
}
