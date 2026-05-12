package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.RoleMenuDto;
import com.kgplatform.system.domain.po.RoleMenu;
import com.kgplatform.system.domain.vo.RoleMenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * 角色菜单关系 Mapper 接口
 * <p>
 * RoleMenuMapper Mapper 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Mapper
@Component
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 分页查询角色菜单关系
     *
     * @param page 分页参数
     * @param vo 查询条件
     * @return 分页结果
     */
    Page<RoleMenuDto> selectPageList(Page<RoleMenuDto> page, @Param("vo") RoleMenuVo vo);

    /**
     * 查询角色下所有菜单关系，包含逻辑删除数据，用于批量授权时恢复历史关系。
     *
     * @param roleId 角色主键
     * @return 角色菜单关系
     */
    List<RoleMenu> selectAllByRoleId(@Param("roleId") Long roleId);

    /**
     * 更新关系状态，绕过逻辑删除过滤，用于批量授权恢复已删除关系。
     *
     * @param id 主键
     * @param status 状态
     * @param deleteStatus 删除状态
     * @return 更新行数
     */
    int updateRelationState(@Param("id") Long id,
                            @Param("status") Boolean status,
                            @Param("deleteStatus") Boolean deleteStatus);
}
