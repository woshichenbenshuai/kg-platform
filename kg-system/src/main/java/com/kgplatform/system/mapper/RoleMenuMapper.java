package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.RoleMenuDto;
import com.kgplatform.system.domain.po.RoleMenu;
import com.kgplatform.system.domain.vo.RoleMenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
/**
 * è§è²èåå
³ç³» Mapper æ¥å£
 * <p>
 * RoleMenuMapper Mapper æ¥å£
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Mapper
@Component
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 分页查询角色菜单关系
Â³Ã§Â³Â»
     *
     * @param page 分页参数
     * @param vo 查询条件
     * @return 分页结果
     */
    Page<RoleMenuDto> selectPageList(Page<RoleMenuDto> page, @Param("vo") RoleMenuVo vo);
}
