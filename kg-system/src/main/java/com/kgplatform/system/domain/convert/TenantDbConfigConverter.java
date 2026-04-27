package com.kgplatform.system.domain.convert;

import com.kgplatform.system.domain.dto.TenantDbConfigDto;
import com.kgplatform.system.domain.po.TenantDbConfig;
import com.kgplatform.system.domain.vo.TenantDbConfigVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户数据库配置转换器
 */
@Mapper
public interface TenantDbConfigConverter {

    TenantDbConfigConverter INSTANCE = Mappers.getMapper(TenantDbConfigConverter.class);

    @Mapping(source = "bindTenantId", target = "tenantId")
    @Mapping(target = "dbPasswordEncrypted", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    TenantDbConfig vo2Domain(TenantDbConfigVo vo);

    TenantDbConfigDto domain2Dto(TenantDbConfig entity);

    List<TenantDbConfigDto> domains2Dtos(List<TenantDbConfig> entitys);
}
