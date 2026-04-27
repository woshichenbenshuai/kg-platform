package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.UserTenantRoleDto;
import com.kgplatform.system.domain.po.UserTenantRole;
import com.kgplatform.system.domain.vo.UserTenantRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserTenantRoleMapper extends BaseMapper<UserTenantRole> {

    Page<UserTenantRoleDto> selectPageList(Page<UserTenantRoleDto> page, @Param("vo") UserTenantRoleVo vo);
}
