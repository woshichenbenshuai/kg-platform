package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.RoleMenuDto;
import com.kgplatform.system.domain.po.RoleMenu;
import com.kgplatform.system.domain.vo.RoleMenuBatchSaveVo;
import com.kgplatform.system.domain.vo.RoleMenuVo;
/**
 * 角色菜单关系 Service 接口
 * <p>
 * IRoleMenuService Service 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     * 分页查询角色菜单关系
     *
     * @param current 当前页码
     * @param size 每页条数
     * @param vo 查询条件
     * @return 分页结果
     */
    Page<RoleMenuDto> selectPage(Integer current, Integer size, RoleMenuVo vo);

    /**
     * 新增角色菜单关系
     *
     * @param vo 入参
     * @return 新增结果
     */
    boolean saveRoleMenu(RoleMenuVo vo);

    /**
     * 批量保存角色菜单授权，以传入菜单集合为准覆盖该角色当前有效授权。
     *
     * @param vo 入参
     * @return 保存结果
     */
    boolean saveRoleMenus(RoleMenuBatchSaveVo vo);

    /**
     * 修改角色菜单关系
     *
     * @param vo 修改条件
     * @return 修改结果
     */
    Boolean update(RoleMenuVo vo);

    /**
     * 删除角色菜单关系
     *
     * @param id 主键
     * @return 删除结果
     */
    boolean delete(Long id);
}
