package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.UserRoleDto;
import com.kgplatform.system.domain.po.UserRole;
import com.kgplatform.system.domain.vo.UserRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserRoleMapper extends BaseMapper<UserRole> {
    Page<UserRoleDto> selectPageList(Page<UserRoleDto> page, @Param("vo") UserRoleVo vo);
}
