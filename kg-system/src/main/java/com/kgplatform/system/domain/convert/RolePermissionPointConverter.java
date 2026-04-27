package com.kgplatform.system.domain.convert;

import com.kgplatform.system.domain.dto.RolePermissionPointDto;
import com.kgplatform.system.domain.po.RolePermissionPoint;
import com.kgplatform.system.domain.vo.RolePermissionPointVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 角色权限点关系
 * <p>
 * RolePermissionPoint表转换器
 *
 * @author Claude
 * @since 2026-04-24 23:59:00
 */
@Mapper(componentModel = "spring")
public interface RolePermissionPointConverter {

    RolePermissionPointConverter INSTANCE = Mappers.getMapper(RolePermissionPointConverter.class);

    @Mapping(source = "bindRoleId", target = "roleId")
    @Mapping(source = "bindPermissionPointId", target = "permissionPointId")
    RolePermissionPoint vo2Domain(RolePermissionPointVo vo);

    RolePermissionPointDto domain2Dto(RolePermissionPoint entity);

    List<RolePermissionPointDto> domains2Dtos(List<RolePermissionPoint> entities);
}
