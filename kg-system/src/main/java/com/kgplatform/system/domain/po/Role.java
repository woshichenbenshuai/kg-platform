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
 * 系统角色
 * <p>
 * Role表实体类
 *
 * @author kg_chen
 * @since 2026-04-24 22:10:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("sys_role")
@Schema(description = "系统角色")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseAuditingEntity<Long> implements Serializable {

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @TableField(value = "role_code")
    @Size(max = 100, message = "角色编码字段过长")
    private String roleCode;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @TableField(value = "role_name")
    @Size(max = 100, message = "角色名称字段过长")
    private String roleName;

    /**
     * 角色范围
     */
    @Schema(description = "角色范围")
    @TableField(value = "role_scope")
    @Size(max = 20, message = "角色范围字段过长")
    private String roleScope;

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
