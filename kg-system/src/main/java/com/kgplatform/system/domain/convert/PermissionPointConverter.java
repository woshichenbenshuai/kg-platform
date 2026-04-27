package com.kgplatform.system.domain.convert;

import com.kgplatform.system.domain.dto.PermissionPointDto;
import com.kgplatform.system.domain.po.PermissionPoint;
import com.kgplatform.system.domain.vo.PermissionPointVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统权限点
 * <p>
 * PermissionPoint表转换器
 *
 * @author Claude
 * @since 2026-04-24 23:59:00
 */
@Mapper(componentModel = "spring")
public interface PermissionPointConverter {

    PermissionPointConverter INSTANCE = Mappers.getMapper(PermissionPointConverter.class);

    PermissionPoint vo2Domain(PermissionPointVo vo);

    PermissionPointDto domain2Dto(PermissionPoint entity);

    List<PermissionPointDto> domains2Dtos(List<PermissionPoint> entities);
}
