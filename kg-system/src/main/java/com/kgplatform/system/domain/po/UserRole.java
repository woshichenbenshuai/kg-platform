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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("user_role")
@Schema(description = "User role relation")
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseAuditingEntity<Long> implements Serializable {

    @Schema(description = "User id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "Role id")
    @TableField("role_id")
    private Long roleId;

    @Schema(description = "Enabled status")
    @TableField("status")
    private Boolean status;

    @Schema(description = "Logical delete flag")
    @TableField("delete_status")
    private Boolean deleteStatus;
}
