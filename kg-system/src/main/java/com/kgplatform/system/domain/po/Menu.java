package com.kgplatform.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.kgplatform.common.core.domain.BaseAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 系统菜单
 * <p>
 * Menu表实体类
 *
 * @author kg_chen
 * @since 2026-04-23 22:30:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("sys_menu")
@Schema(description = "系统菜单")
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseAuditingEntity<Long> implements Serializable {

    /**
     * 菜单编码
     */
    @Schema(description = "菜单编码")
    @TableField(value = "menu_code")
    @Size(max = 100, message = "菜单编码字段过长")
    private String menuCode;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    @TableField(value = "menu_name")
    @Size(max = 100, message = "菜单名称字段过长")
    private String menuName;

    /**
     * 菜单范围
     */
    @Schema(description = "菜单范围")
    @TableField(value = "menu_scope")
    @Size(max = 20, message = "菜单范围字段过长")
    private String menuScope;

    /**
     * 父级菜单id
     */
    @Schema(description = "父级菜单id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 路由路径
     */
    @Schema(description = "路由路径")
    @TableField(value = "route_path")
    @Size(max = 255, message = "路由路径字段过长")
    private String routePath;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    @TableField(value = "component_path")
    @Size(max = 255, message = "组件路径字段过长")
    private String componentPath;

    /**
     * 图标
     */
    @Schema(description = "图标")
    @TableField(value = "icon")
    @Size(max = 100, message = "图标字段过长")
    private String icon;

    /**
     * 是否显示 1显示 0隐藏
     */
    @Schema(description = "是否显示 1显示 0隐藏")
    @TableField(value = "visible")
    private Boolean visible;

    /**
     * 是否缓存 1缓存 0不缓存
     */
    @Schema(description = "是否缓存 1缓存 0不缓存")
    @TableField(value = "keep_alive")
    private Boolean keepAlive;

    /**
     * 排序
     */
    @Schema(description = "排序")
    @TableField(value = "sort_no")
    private Integer sortNo;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @TableField(value = "remarks")
    @Size(max = 255, message = "备注字段过长")
    private String remarks;

    /**
     * 状态 1启用 0禁用
     */
    @Schema(description = "状态 1启用 0禁用")
    @TableField(value = "status")
    private Boolean status;

    /**
     * 是否删除 1删除  0未删除
     */
    @Schema(description = "是否删除 1删除  0未删除")
    @TableField(value = "delete_status")
    private Boolean deleteStatus;
}
