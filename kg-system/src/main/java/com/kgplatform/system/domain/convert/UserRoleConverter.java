package com.kgplatform.system.domain.convert;

import com.kgplatform.system.domain.dto.UserRoleDto;
import com.kgplatform.system.domain.po.UserRole;
import com.kgplatform.system.domain.vo.UserRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户角色关系转换器
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
 */
@Mapper(componentModel = "spring")
public interface UserRoleConverter {

    UserRoleConverter INSTANCE = Mappers.getMapper(UserRoleConverter.class);

    @Mapping(source = "bindUserId", target = "userId")
    @Mapping(source = "bindRoleId", target = "roleId")
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    UserRole vo2Domain(UserRoleVo vo);

    UserRoleDto domain2Dto(UserRole entity);

    List<UserRoleDto> domains2Dtos(List<UserRole> entities);
}

