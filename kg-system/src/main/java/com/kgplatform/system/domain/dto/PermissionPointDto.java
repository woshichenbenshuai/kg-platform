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
 * 系统权限点
 * <p>
 * PermissionPoint表DTO
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
@Schema(description = "系统权限点")
public class PermissionPointDto implements Serializable {

    /**
     * ID主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 权限编码
     */
    @Schema(description = "权限编码")
    @Size(max = 100, message = "权限编码字段过长")
    private String permissionCode;

    /**
     * 权限名称
     */
    @Schema(description = "权限名称")
    @Size(max = 100, message = "权限名称字段过长")
    private String permissionName;

    /**
     * 权限类型
     */
    @Schema(description = "权限类型")
    @Size(max = 20, message = "权限类型字段过长")
    private String permissionType;

    /**
     * 权限范围
     */
    @Schema(description = "权限范围")
    @Size(max = 20, message = "权限范围字段过长")
    private String permissionScope;

    /**
     * 绑定菜单ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindMenuId;

    /**
     * API路径
     */
    @Schema(description = "API路径")
    @Size(max = 255, message = "API路径字段过长")
    private String apiPath;

    /**
     * API请求方法
     */
    @Schema(description = "API请求方法")
    @Size(max = 20, message = "API请求方法字段过长")
    private String apiMethod;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, message = "备注字段过长")
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
