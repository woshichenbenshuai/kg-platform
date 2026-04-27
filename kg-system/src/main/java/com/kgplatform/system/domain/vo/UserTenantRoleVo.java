package com.kgplatform.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 用户租户角色关系
 * <p>
 * UserTenantRole表入参类
 *
 * @author kg_chen
 * @since 2026-04-24 18:30:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "用户租户角色关系")
@EqualsAndHashCode(callSuper = true)
public class UserTenantRoleVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 绑定用户租户关系id
     */
    @Schema(description = "绑定用户租户关系id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindUserTenantId;

    /**
     * 绑定角色id
     */
    @Schema(description = "绑定角色id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindRoleId;

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
