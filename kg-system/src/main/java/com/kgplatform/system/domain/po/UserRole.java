package com.kgplatform.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgplatform.common.core.domain.BaseAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户角色关系
 * <p>
 * UserRole实体类
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("user_role")
@Schema(description = "用户角色关系")
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseAuditingEntity<Long> implements Serializable {

    /**
     * 用户主键
     */
    @Schema(description = "用户主键")
    @TableField("user_id")
    private Long userId;

    /**
     * 角色主键
     */
    @Schema(description = "角色主键")
    @TableField("role_id")
    private Long roleId;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    @TableField("status")
    private Boolean status;

    /**
     * 是否删除 1删除 0未删除
     */
    @Schema(description = "是否删除 1删除 0未删除")
    @TableField("delete_status")
    private Boolean deleteStatus;
}
