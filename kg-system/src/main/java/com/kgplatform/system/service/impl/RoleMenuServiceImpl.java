package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.RoleMenuConverter;
import com.kgplatform.system.domain.dto.RoleMenuDto;
import com.kgplatform.system.domain.po.RoleMenu;
import com.kgplatform.system.domain.vo.RoleMenuVo;
import com.kgplatform.system.mapper.RoleMenuMapper;
import com.kgplatform.system.service.IRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 角色菜单关系 Service 实现类
 * <p>
 * RoleMenuServiceImpl Service 实现类
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Service("roleMenuService")
@Transactional(rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    private final RoleMenuConverter roleMenuConverter;

    public RoleMenuServiceImpl(RoleMenuConverter roleMenuConverter) {
        this.roleMenuConverter = roleMenuConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleMenuDto> selectPage(Integer current, Integer size, RoleMenuVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    public boolean saveRoleMenu(RoleMenuVo vo) {
        Asserts.notNull(vo, "角色菜单参数不能为空");
        Asserts.notNull(vo.getBindRoleId(), "角色主键不能为空");
        Asserts.notNull(vo.getBindMenuId(), "菜单主键不能为空");
        long count = baseMapper.selectCount(Wrappers.<RoleMenu>lambdaQuery()
                .eq(RoleMenu::getRoleId, vo.getBindRoleId())
                .eq(RoleMenu::getMenuId, vo.getBindMenuId())
                .eq(RoleMenu::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "角色菜单关系已存在");
        return super.save(roleMenuConverter.vo2Domain(vo));
    }

    @Override
    public Boolean update(RoleMenuVo vo) {
        Asserts.notNull(vo, "角色菜单参数不能为空");
        Asserts.notNull(vo.getId(), "主键不能为空");
        RoleMenu old = super.getById(vo.getId());
        Asserts.notNull(old, "角色菜单关系不存在");
        long count = baseMapper.selectCount(Wrappers.<RoleMenu>lambdaQuery()
                .eq(RoleMenu::getRoleId, vo.getBindRoleId())
                .eq(RoleMenu::getMenuId, vo.getBindMenuId())
                .ne(RoleMenu::getId, vo.getId())
                .eq(RoleMenu::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "角色菜单关系已存在");
        return super.updateById(roleMenuConverter.vo2Domain(vo));
    }

    @Override
    public boolean delete(Long id) {
        Asserts.notNull(id, "主键不能为空");
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setId(id);
        roleMenu.setDeleteStatus(Boolean.TRUE);
        return super.updateById(roleMenu);
    }
}
