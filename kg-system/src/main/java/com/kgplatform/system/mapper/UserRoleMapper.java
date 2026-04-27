package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.UserRoleDto;
import com.kgplatform.system.domain.po.UserRole;
import com.kgplatform.system.domain.vo.UserRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 用户角色关系 Mapper 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
 */
@Mapper
@Component
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 分页查询用户角色关系
     *
     * @param page 分页参数
     * @param vo   查询条件
     * @return 分页结果
     */
    Page<UserRoleDto> selectPageList(Page<UserRoleDto> page, @Param("vo") UserRoleVo vo);
}
