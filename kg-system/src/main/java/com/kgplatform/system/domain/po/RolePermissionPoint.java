package com.kgplatform.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgplatform.common.core.domain.BaseAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色权限点关系
 * <p>
 * RolePermissionPoint表实体类
 *
 * @author Claude
 * @since 2026-04-24 23:59:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("role_permission_point")
@Schema(description = "角色权限点关系")
@EqualsAndHashCode(callSuper = true)
public class RolePermissionPoint extends BaseAuditingEntity<Long> implements Serializable {

    /**
     * 角色主键
     */
    @Schema(description = "角色主键")
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 权限点主键
     */
    @Schema(description = "权限点主键")
    @TableField(value = "permission_point_id")
    private Long permissionPointId;

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
