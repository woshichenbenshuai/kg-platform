package com.kgplatform.auth.domain.po;

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

/**
 * 认证用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("sys_user")
@Schema(description = "认证用户")
@EqualsAndHashCode(callSuper = true)
public class AuthUser extends BaseAuditingEntity<Long> {

    @TableField("username")
    @Schema(description = "登录账号")
    private String username;

    @TableField("nickname")
    @Schema(description = "昵称")
    private String nickname;

    @TableField("password")
    @Schema(description = "登录密码")
    private String password;

    @TableField("status")
    @Schema(description = "状态，1启用 0禁用")
    private Integer status;
}
