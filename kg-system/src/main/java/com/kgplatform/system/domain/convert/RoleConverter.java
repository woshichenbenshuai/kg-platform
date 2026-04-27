package com.kgplatform.system.domain.convert;

import com.kgplatform.system.domain.dto.RoleDto;
import com.kgplatform.system.domain.po.Role;
import com.kgplatform.system.domain.vo.RoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统角色转换器
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {

    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    Role vo2Domain(RoleVo vo);

    RoleDto domain2Dto(Role entity);

    List<RoleDto> domains2Dtos(List<Role> entitys);
}
