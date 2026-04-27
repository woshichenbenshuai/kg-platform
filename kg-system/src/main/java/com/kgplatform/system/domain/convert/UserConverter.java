package com.kgplatform.system.domain.convert;

import com.kgplatform.system.domain.dto.UserDto;
import com.kgplatform.system.domain.po.User;
import com.kgplatform.system.domain.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统用户转换器
 */
@Mapper
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    User vo2Domain(UserVo vo);

    UserDto domain2Dto(User entity);

    List<UserDto> domains2Dtos(List<User> entitys);
}
