package com.kgplatform.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.core.domain.BaseAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户租户关系
 * <p>
 * UserTenant实体类
 *
 * @author kg_chen
 * @since 2026-04-23 22:40:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("sys_user_tenant")
@Schema(description = "用户租户关系")
@EqualsAndHashCode(callSuper = true)
public class UserTenant extends BaseAuditingEntity<Long> implements Serializable {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 租户id
     */
    @Schema(description = "租户id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableField(value = "tenant_id")
    private Long tenantId;

    /**
     * 身份类型
     */
    @Schema(description = "身份类型")
    @TableField(value = "identity_type")
    @Size(max = 20, message = "身份类型字段过长")
    private String identityType;

    /**
     * 是否默认租户 1是 0否
     */
    @Schema(description = "是否默认租户 1是 0否")
    @TableField(value = "default_flag")
    private Boolean defaultFlag;

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
