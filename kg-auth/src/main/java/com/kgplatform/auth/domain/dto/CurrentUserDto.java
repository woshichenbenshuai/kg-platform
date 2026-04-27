package com.kgplatform.auth.domain.dto;

import com.kgplatform.system.domain.dto.MenuDto;
import com.kgplatform.system.domain.dto.PermissionPointDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 当前登录用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@Schema(description = "当前登录用户")
public class CurrentUserDto {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "登录账号")
    private String username;

    @Schema(description = "租户主键")
    private Long tenantId;

    @Schema(description = "角色编码集合")
    private List<String> roleCodes;

    @Schema(description = "角色名称集合")
    private List<String> roleNames;

    @Schema(description = "菜单集合")
    private List<MenuDto> menus;

    @Schema(description = "权限编码集合")
    private List<String> permissionCodes;

    @Schema(description = "权限点集合")
    private List<PermissionPointDto> permissionPoints;
}
