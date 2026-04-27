package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.RolePermissionPointDto;
import com.kgplatform.system.domain.po.RolePermissionPoint;
import com.kgplatform.system.domain.vo.RolePermissionPointVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 角色权限点关系
 * <p>
 * RolePermissionPoint表 Mapper 接口
 *
 * @author Claude
 * @since 2026-04-24 23:59:00
 */
@Mapper
@Component
public interface RolePermissionPointMapper extends BaseMapper<RolePermissionPoint> {

    /**
     * 分页查询角色权限点关系
     *
     * @param page page
     * @param vo vo
     * @return Page<RolePermissionPointDto>
     */
    Page<RolePermissionPointDto> selectPageList(Page<RolePermissionPointDto> page,
                                                @Param("vo") RolePermissionPointVo vo);
}
