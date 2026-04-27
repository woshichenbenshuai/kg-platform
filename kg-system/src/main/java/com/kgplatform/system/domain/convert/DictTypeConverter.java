package com.kgplatform.system.domain.convert;

import com.kgplatform.system.domain.dto.DictTypeDto;
import com.kgplatform.system.domain.po.DictType;
import com.kgplatform.system.domain.vo.DictTypeVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统字典类型转换器
 */
@Mapper(componentModel = "spring")
public interface DictTypeConverter {

    DictTypeConverter INSTANCE = Mappers.getMapper(DictTypeConverter.class);

    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    DictType vo2Domain(DictTypeVo vo);

    DictTypeDto domain2Dto(DictType entity);

    List<DictTypeDto> domains2Dtos(List<DictType> entitys);
}
