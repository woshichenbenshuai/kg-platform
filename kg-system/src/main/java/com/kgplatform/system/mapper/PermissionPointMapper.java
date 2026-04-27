package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.PermissionPointDto;
import com.kgplatform.system.domain.po.PermissionPoint;
import com.kgplatform.system.domain.vo.PermissionPointVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 系统权限点
 * <p>
 * PermissionPoint表 Mapper 接口
 *
 * @author Claude
 * @since 2026-04-24 23:59:00
 */
@Mapper
@Component
public interface PermissionPointMapper extends BaseMapper<PermissionPoint> {

    /**
     * 分页查询系统权限点
     *
     * @param page page
     * @param vo vo
     * @return Page<PermissionPointDto>
     */
    Page<PermissionPointDto> selectPageList(Page<PermissionPointDto> page,
                                            @Param("vo") PermissionPointVo vo);
}
