package com.kgplatform.system.domain.convert;

import com.kgplatform.system.domain.dto.TenantDto;
import com.kgplatform.system.domain.po.Tenant;
import com.kgplatform.system.domain.vo.TenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统租户转换器
 */
@Mapper
public interface TenantConverter {

    TenantConverter INSTANCE = Mappers.getMapper(TenantConverter.class);

    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    Tenant vo2Domain(TenantVo vo);

    TenantDto domain2Dto(Tenant entity);

    List<TenantDto> domains2Dtos(List<Tenant> entitys);
}
