package com.kgplatform.system.domain.convert;

import com.kgplatform.system.domain.dto.UserTenantRoleDto;
import com.kgplatform.system.domain.po.UserTenantRole;
import com.kgplatform.system.domain.vo.UserTenantRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户租户角色关系转换器
 */
@Mapper
public interface UserTenantRoleConverter {

    UserTenantRoleConverter INSTANCE = Mappers.getMapper(UserTenantRoleConverter.class);

    @Mapping(source = "bindUserTenantId", target = "userTenantId")
    @Mapping(source = "bindRoleId", target = "roleId")
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    UserTenantRole vo2Domain(UserTenantRoleVo vo);

    UserTenantRoleDto domain2Dto(UserTenantRole entity);

    List<UserTenantRoleDto> domains2Dtos(List<UserTenantRole> entitys);
}
