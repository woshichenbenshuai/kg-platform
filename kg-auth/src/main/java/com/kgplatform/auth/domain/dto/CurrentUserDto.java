package com.kgplatform.auth.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.system.domain.dto.CurrentUserTenantDto;
import com.kgplatform.system.domain.dto.MenuDto;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "用户主键")
    private Long userId;

    @Schema(description = "登录账号")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "租户主键")
    private Long tenantId;

    @Schema(description = "角色编码集合")
    private List<String> roleCodes;

    @Schema(description = "角色名称集合")
    private List<String> roleNames;

    @Schema(description = "菜单集合")
    private List<MenuDto> menus;

    private List<CurrentUserTenantDto> tenants;
}
