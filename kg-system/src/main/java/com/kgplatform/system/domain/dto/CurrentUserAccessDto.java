package com.kgplatform.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 当前用户访问聚合
 * <p>
 * CurrentUserAccessDto返回对象
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "当前用户访问聚合")
public class CurrentUserAccessDto implements Serializable {

    /**
     * 用户主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "用户主键")
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
    @Schema(description = "租户主键")
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
}
