
package com.kgplatform.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 系统用户
 * <p>
 * UserVo入参对象
¥åå¯¹è±¡
 *
 * @author kg_chen
 * @since 2026-04-23 20:40:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "系统用户")
@EqualsAndHashCode(callSuper = true)
public class UserVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 登录账号
     */
    @Schema(description = "登录账号")
    @Size(max = 100, message = "登录账号字段过长")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @Size(max = 100, message = "昵称字段过长")
    private String nickname;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @Size(max = 20, message = "手机号字段过长")
    private String phone;

    /**
     * 登录密码
     */
    @Schema(description = "登录密码")
    @Size(max = 255, message = "登录密码字段过长")
    private String password;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    private Integer status;

    /**
     * 是否删除 1删除  0未删除
     */
    @Schema(description = "是否删除 1删除  0未删除")
    private Boolean deleteStatus;

    @Schema(description = "绑定角色ID集合")
    private List<Long> roleIds;
}
