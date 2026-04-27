package com.kgplatform.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 当前用户权限聚合
 * <p>
 * CurrentUserPermissionDto表DTO
 *
 * @author Claude
 * @since 2026-04-24 23:59:00
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "当前用户权限聚合")
public class CurrentUserPermissionDto implements Serializable {

    /**
     * 用户主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    /**
     * 登录账号
     */
    @Schema(description = "登录账号")
    private String username;

    /**
     * 租户主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;

    /**
     * 角色编码集合
     */
    @Schema(description = "角色编码集合")
    private List<String> roleCodes;

    /**
     * 角色名称集合
     */
    @Schema(description = "角色名称集合")
    private List<String> roleNames;

    /**
     * 菜单集合
     */
    @Schema(description = "菜单集合")
    private List<MenuDto> menus;

    /**
     * 权限编码集合
     */
    @Schema(description = "权限编码集合")
    private List<String> permissionCodes;

    /**
     * 权限点集合
     */
    @Schema(description = "权限点集合")
    private List<PermissionPointDto> permissionPoints;
}
