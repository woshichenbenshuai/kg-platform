package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.MenuDto;
import com.kgplatform.system.domain.po.Menu;
import com.kgplatform.system.domain.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
/**
 * 系统菜单 Mapper 接口
 * <p>
 * MenuMapper Mapper 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Mapper
@Component
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 分页查询系统菜单
     *
     * @param page 分页参数
     * @param vo 查询条件
     * @return 分页结果
     */
    Page<MenuDto> selectPageList(Page<MenuDto> page, @Param("vo") MenuVo vo);
}
