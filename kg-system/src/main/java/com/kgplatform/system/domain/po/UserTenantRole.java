package com.kgplatform.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.core.domain.BaseAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户租户角色关系
 * <p>
 * UserTenantRole表实体类
 *
 * @author kg_chen
 * @since 2026-04-24 18:30:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("user_tenant_role")
@Schema(description = "用户租户角色关系")
@EqualsAndHashCode(callSuper = true)
public class UserTenantRole extends BaseAuditingEntity<Long> implements Serializable {

    /**
     * 用户租户关系id
     */
    @Schema(description = "用户租户关系id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableField(value = "user_tenant_id")
    private Long userTenantId;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableField(value = "role_id")
    private Long roleId;

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
