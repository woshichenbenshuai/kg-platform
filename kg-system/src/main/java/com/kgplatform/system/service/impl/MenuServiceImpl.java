package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.MenuConverter;
import com.kgplatform.system.domain.dto.MenuDto;
import com.kgplatform.system.domain.po.Menu;
import com.kgplatform.system.domain.vo.MenuVo;
import com.kgplatform.system.mapper.MenuMapper;
import com.kgplatform.system.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("menuService")
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    private final MenuConverter menuConverter;

    public MenuServiceImpl(MenuConverter menuConverter) {
        this.menuConverter = menuConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuDto> selectPage(Integer current, Integer size, MenuVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    public boolean saveMenu(MenuVo vo) {
        Asserts.notNull(vo, "菜单参数不能为空");
        Asserts.notBlank(vo.getMenuCode(), "菜单编码不能为空");
        Asserts.notBlank(vo.getMenuName(), "菜单名称不能为空");
        long count = baseMapper.selectCount(Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getMenuCode, vo.getMenuCode())
                .eq(Menu::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "菜单编码已存在");
        return super.save(menuConverter.vo2Domain(vo));
    }

    @Override
    public Boolean update(MenuVo vo) {
        return super.updateById(menuConverter.vo2Domain(vo));
    }

    @Override
    public boolean delete(Long id) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setDeleteStatus(Boolean.TRUE);
        return super.updateById(menu);
    }
}
