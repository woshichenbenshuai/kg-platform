package com.kgplatform.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgplatform.common.core.domain.BaseAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 系统权限点
 * <p>
 * PermissionPoint表实体类
 *
 * @author Claude
 * @since 2026-04-24 23:59:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("sys_permission_point")
@Schema(description = "系统权限点")
@EqualsAndHashCode(callSuper = true)
public class PermissionPoint extends BaseAuditingEntity<Long> implements Serializable {

    /**
     * 权限编码
     */
    @Schema(description = "权限编码")
    @TableField(value = "permission_code")
    @Size(max = 100, message = "权限编码字段过长")
    private String permissionCode;

    /**
     * 权限名称
     */
    @Schema(description = "权限名称")
    @TableField(value = "permission_name")
    @Size(max = 100, message = "权限名称字段过长")
    private String permissionName;

    /**
     * 权限类型
     */
    @Schema(description = "权限类型")
    @TableField(value = "permission_type")
    @Size(max = 20, message = "权限类型字段过长")
    private String permissionType;

    /**
     * 权限范围
     */
    @Schema(description = "权限范围")
    @TableField(value = "permission_scope")
    @Size(max = 20, message = "权限范围字段过长")
    private String permissionScope;

    /**
     * 绑定菜单ID
     */
    @Schema(description = "绑定菜单ID")
    @TableField(value = "bind_menu_id")
    private Long bindMenuId;

    /**
     * API路径
     */
    @Schema(description = "API路径")
    @TableField(value = "api_path")
    @Size(max = 255, message = "API路径字段过长")
    private String apiPath;

    /**
     * API请求方法
     */
    @Schema(description = "API请求方法")
    @TableField(value = "api_method")
    @Size(max = 20, message = "API请求方法字段过长")
    private String apiMethod;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @TableField(value = "remarks")
    @Size(max = 500, message = "备注字段过长")
    private String remarks;

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
