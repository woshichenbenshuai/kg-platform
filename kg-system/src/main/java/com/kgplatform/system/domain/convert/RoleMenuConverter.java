package com.kgplatform.system.domain.convert;

import com.kgplatform.system.domain.dto.RoleMenuDto;
import com.kgplatform.system.domain.po.RoleMenu;
import com.kgplatform.system.domain.vo.RoleMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 角色菜单关系转换器
 */
@Mapper(componentModel = "spring")
public interface RoleMenuConverter {

    RoleMenuConverter INSTANCE = Mappers.getMapper(RoleMenuConverter.class);

    @Mapping(source = "bindRoleId", target = "roleId")
    @Mapping(source = "bindMenuId", target = "menuId")
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    RoleMenu vo2Domain(RoleMenuVo vo);

    RoleMenuDto domain2Dto(RoleMenu entity);

    List<RoleMenuDto> domains2Dtos(List<RoleMenu> entities);
}
