package com.kgplatform.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgplatform.common.core.domain.BaseAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色菜单关系
 * <p>
 * RoleMenu表实体类
 *
 * @author kg_chen
 * @since 2026-04-24 23:50:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("role_menu")
@Schema(description = "角色菜单关系")
@EqualsAndHashCode(callSuper = true)
public class RoleMenu extends BaseAuditingEntity<Long> implements Serializable {

    /**
     * 角色主键
     */
    @Schema(description = "角色主键")
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 菜单主键
     */
    @Schema(description = "菜单主键")
    @TableField(value = "menu_id")
    private Long menuId;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    @TableField(value = "status")
    private Boolean status;

    /**
     * 是否删除 1删除  0未删除
     */
    @Schema(description = "是否删除 1删除  0未删除")
    @TableField(value = "delete_status")
    private Boolean deleteStatus;
}
