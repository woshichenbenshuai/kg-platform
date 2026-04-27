package com.kgplatform.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kgplatform.common.web.core.AppConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统菜单
 * <p>
 * MenuDto返回对象
 *
 * @author kg_chen
 * @since 2026-04-23 22:30:00
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "系统菜单")
public class MenuDto implements Serializable {

    /**
     * ID主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 菜单编码
     */
    @Schema(description = "菜单编码")
    @Size(max = 100, message = "菜单编码字段过长")
    private String menuCode;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    @Size(max = 100, message = "菜单名称字段过长")
    private String menuName;

    /**
     * 菜单范围
     */
    @Schema(description = "菜单范围")
    @Size(max = 20, message = "菜单范围字段过长")
    private String menuScope;

    /**
     * 父级菜单id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;

    /**
     * 路由路径
     */
    @Schema(description = "路由路径")
    @Size(max = 255, message = "路由路径字段过长")
    private String routePath;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    @Size(max = 255, message = "组件路径字段过长")
    private String componentPath;

    /**
     * 图标
     */
    @Schema(description = "图标")
    @Size(max = 100, message = "图标字段过长")
    private String icon;

    /**
     * 是否显示 1显示 0隐藏
     */
    @Schema(description = "是否显示 1显示 0隐藏")
    private Boolean visible;

    /**
     * 是否缓存 1缓存 0不缓存
     */
    @Schema(description = "是否缓存 1缓存 0不缓存")
    private Boolean keepAlive;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sortNo;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 255, message = "备注字段过长")
    private String remarks;

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

    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(timezone = "GMT+8", pattern = AppConstant.FORMAT_PATTERN_DATE_TIME)
    private LocalDateTime createTime;
}
