
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
 * 系统用户
 * <p>
 * User实体类
 *
 * @author kg_chen
 * @since 2026-04-23 20:40:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("sys_user")
@Schema(description = "系统用户")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseAuditingEntity<Long> implements Serializable {

    /**
     * 登录账号
     */
    @Schema(description = "登录账号")
    @TableField(value = "username")
    @Size(max = 100, message = "登录账号字段过长")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @TableField(value = "nickname")
    @Size(max = 100, message = "昵称字段过长")
    private String nickname;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @TableField(value = "phone")
    @Size(max = 20, message = "手机号字段过长")
    private String phone;

    /**
     * 登录密码
     */
    @Schema(description = "登录密码")
    @TableField(value = "password")
    @Size(max = 255, message = "登录密码字段过长")
    private String password;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    @TableField(value = "status")
    private Integer status;

    /**
     * 是否删除 1删除  0未删除
     */
    @Schema(description = "是否删除 1删除  0未删除")
    @TableField(value = "delete_status")
    private Boolean deleteStatus;
}
