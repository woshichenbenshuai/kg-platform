package com.kgplatform.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

/**
 * 用户租户关系
 * <p>
 * UserTenant表入参类
 *
 * @author kg_chen
 * @since 2026-04-23 22:40:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "用户租户关系")
@EqualsAndHashCode(callSuper = true)
public class UserTenantVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 绑定用户id
     */
    @Schema(description = "绑定用户id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindUserId;

    /**
     * 绑定租户id
     */
    @Schema(description = "绑定租户id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindTenantId;

    /**
     * 身份类型
     */
    @Schema(description = "身份类型")
    @Size(max = 20, message = "身份类型字段过长")
    private String identityType;

    /**
     * 是否默认租户 1是 0否
     */
    @Schema(description = "是否默认租户 1是 0否")
    private Boolean defaultFlag;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    private Boolean status;

    /**
     * 是否删除 1删除  0未删除
     */
    @Schema(description = "是否删除 1删除  0未删除")
    private Boolean deleteStatus;
}
