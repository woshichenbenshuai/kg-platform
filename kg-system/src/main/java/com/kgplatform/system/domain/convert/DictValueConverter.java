package com.kgplatform.system.domain.convert;

import com.kgplatform.system.domain.dto.DictValueDto;
import com.kgplatform.system.domain.po.DictValue;
import com.kgplatform.system.domain.vo.DictValueVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统字典数据转换器
 */
@Mapper
public interface DictValueConverter {

    DictValueConverter INSTANCE = Mappers.getMapper(DictValueConverter.class);

    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    DictValue vo2Domain(DictValueVo vo);

    DictValueDto domain2Dto(DictValue entity);

    List<DictValueDto> domains2Dtos(List<DictValue> entitys);
}
