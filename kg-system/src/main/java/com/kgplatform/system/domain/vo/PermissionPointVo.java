package com.kgplatform.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统权限点
 * <p>
 * PermissionPoint表入参类
 *
 * @author Claude
 * @since 2026-04-24 23:59:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "系统权限点")
@EqualsAndHashCode(callSuper = true)
public class PermissionPointVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 权限编码
     */
    @Schema(description = "权限编码")
    private String permissionCode;

    /**
     * 权限名称
     */
    @Schema(description = "权限名称")
    private String permissionName;

    /**
     * 权限类型
     */
    @Schema(description = "权限类型")
    private String permissionType;

    /**
     * 权限范围
     */
    @Schema(description = "权限范围")
    private String permissionScope;

    /**
     * 绑定菜单ID
     */
    @Schema(description = "绑定菜单ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindMenuId;

    /**
     * API路径
     */
    @Schema(description = "API路径")
    private String apiPath;

    /**
     * API请求方法
     */
    @Schema(description = "API请求方法")
    private String apiMethod;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remarks;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    private Boolean status;
}
