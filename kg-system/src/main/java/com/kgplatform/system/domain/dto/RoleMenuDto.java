package com.kgplatform.system.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色菜单关系返回对象
 */
@Data
public class RoleMenuDto {

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色主键
     */
    private Long roleId;

    /**
     * 菜单主键
     */
    private Long menuId;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 删除状态
     */
    private Boolean deleteStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
