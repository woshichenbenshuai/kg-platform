package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.MenuDto;
import com.kgplatform.system.domain.po.Menu;
import com.kgplatform.system.domain.vo.MenuVo;
/**
 * 系统菜单 Service 接口
 * <p>
 * IMenuService Service 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

public interface IMenuService extends IService<Menu> {

    /**
     * 分页查询所有数据
     *
     * @param current 当前页码
     * @param size 每页条数
     * @param vo 查询条件
     * @return 分页结果
     */
    Page<MenuDto> selectPage(Integer current, Integer size, MenuVo vo);

    /**
     * 新增菜单
     *
     * @param vo 入参
     * @return 新增结果
     */
    boolean saveMenu(MenuVo vo);

    /**
     * 修改菜单
     *
     * @param vo 修改条件
     * @return 修改结果
     */
    Boolean update(MenuVo vo);

    /**
     * 删除菜单
     *
     * @param id 主键
     * @return 删除结果
     */
    boolean delete(Long id);
}
