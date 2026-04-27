package com.kgplatform.system.domain.convert;

import com.kgplatform.system.domain.dto.UserTenantDto;
import com.kgplatform.system.domain.po.UserTenant;
import com.kgplatform.system.domain.vo.UserTenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户租户关系转换器
 */
@Mapper(componentModel = "spring")
public interface UserTenantConverter {

    UserTenantConverter INSTANCE = Mappers.getMapper(UserTenantConverter.class);

    @Mapping(source = "bindUserId", target = "userId")
    @Mapping(source = "bindTenantId", target = "tenantId")
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    UserTenant vo2Domain(UserTenantVo vo);

    UserTenantDto domain2Dto(UserTenant entity);

    List<UserTenantDto> domains2Dtos(List<UserTenant> entitys);
}
