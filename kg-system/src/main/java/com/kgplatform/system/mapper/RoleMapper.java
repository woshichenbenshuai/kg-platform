package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.RoleDto;
import com.kgplatform.system.domain.po.Role;
import com.kgplatform.system.domain.vo.RoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
/**
 * 系统角色 Mapper 接口
 * <p>
 * RoleMapper Mapper 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Mapper
@Component
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 分页查询系统角色
     *
     * @param page 分页参数
     * @param vo 查询条件
     * @return 分页结果
     */
    Page<RoleDto> selectPageList(Page<RoleDto> page, @Param("vo") RoleVo vo);
}
