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
 * 系统租户
 * <p>
 * Tenant表DTO
 *
 * @author kg_chen
 * @since 2026-04-23 22:50:00
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "系统租户")
public class TenantDto implements Serializable {

    /**
     * ID主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 租户编码
     */
    @Schema(description = "租户编码")
    @Size(max = 100, message = "租户编码字段过长")
    private String tenantCode;

    /**
     * 租户名称
     */
    @Schema(description = "租户名称")
    @Size(max = 100, message = "租户名称字段过长")
    private String tenantName;

    /**
     * 联系人
     */
    @Schema(description = "联系人")
    @Size(max = 100, message = "联系人字段过长")
    private String contactName;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    @Size(max = 20, message = "联系电话字段过长")
    private String contactPhone;

    /**
     * 地址
     */
    @Schema(description = "地址")
    @Size(max = 255, message = "地址字段过长")
    private String address;

    /**
     * 到期日期
     */
    @Schema(description = "到期日期")
    @Size(max = 20, message = "到期日期字段过长")
    private String expireDate;

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
