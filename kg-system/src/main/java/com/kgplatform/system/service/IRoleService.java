package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.RoleDto;
import com.kgplatform.system.domain.po.Role;
import com.kgplatform.system.domain.vo.RoleVo;

public interface IRoleService extends IService<Role> {

    /**
     * 分页查询所有数据。
     *
     * @param current 当前页码
     * @param size 每页条数
     * @param vo 查询条件
     * @return 分页结果
     */
    Page<RoleDto> selectPage(Integer current, Integer size, RoleVo vo);

    /**
     * 新增角色。
     *
     * @param vo 入参
     * @return 新增结果
     */
    boolean saveRole(RoleVo vo);

    /**
     * 修改角色。
     *
     * @param vo 修改条件
     * @return 修改结果
     */
    Boolean update(RoleVo vo);

    /**
     * 删除角色。
     *
     * @param id 主键
     * @return 删除结果
     */
    boolean delete(Long id);
}
