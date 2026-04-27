package com.kgplatform.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 角色权限点关系
 * <p>
 * RolePermissionPoint表入参类
 *
 * @author Claude
 * @since 2026-04-24 23:59:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "角色权限点关系")
@EqualsAndHashCode(callSuper = true)
public class RolePermissionPointVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 绑定角色主键
     */
    @Schema(description = "绑定角色主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindRoleId;

    /**
     * 绑定权限点主键
     */
    @Schema(description = "绑定权限点主键")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindPermissionPointId;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    private Boolean status;
}
