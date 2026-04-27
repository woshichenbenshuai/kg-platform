package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.UserRoleDto;
import com.kgplatform.system.domain.po.UserRole;
import com.kgplatform.system.domain.vo.UserRoleVo;

/**
 * 用户角色关系 Service 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 分页查询用户角色关系
     *
     * @param current 当前页码
     * @param size    每页条数
     * @param vo      查询条件
     * @return 分页结果
     */
    Page<UserRoleDto> selectPage(Integer current, Integer size, UserRoleVo vo);

    /**
     * 新增用户角色关系
     *
     * @param vo 入参
     * @return 新增结果
     */
    boolean saveUserRole(UserRoleVo vo);

    /**
     * 修改用户角色关系
     *
     * @param vo 修改条件
     * @return 修改结果
     */
    Boolean update(UserRoleVo vo);

    /**
     * 删除用户角色关系
     *
     * @param id 主键
     * @return 删除结果
     */
    boolean delete(Long id);
}
