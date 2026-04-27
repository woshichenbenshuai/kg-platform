package com.kgplatform.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgplatform.common.core.domain.BaseAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 系统租户
 * <p>
 * Tenant表实体类
 *
 * @author kg_chen
 * @since 2026-04-23 22:50:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("sys_tenant")
@Schema(description = "系统租户")
@EqualsAndHashCode(callSuper = true)
public class Tenant extends BaseAuditingEntity<Long> implements Serializable {

    /**
     * 租户编码
     */
    @Schema(description = "租户编码")
    @TableField(value = "tenant_code")
    @Size(max = 100, message = "租户编码字段过长")
    private String tenantCode;

    /**
     * 租户名称
     */
    @Schema(description = "租户名称")
    @TableField(value = "tenant_name")
    @Size(max = 100, message = "租户名称字段过长")
    private String tenantName;

    /**
     * 联系人
     */
    @Schema(description = "联系人")
    @TableField(value = "contact_name")
    @Size(max = 100, message = "联系人字段过长")
    private String contactName;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    @TableField(value = "contact_phone")
    @Size(max = 20, message = "联系电话字段过长")
    private String contactPhone;

    /**
     * 地址
     */
    @Schema(description = "地址")
    @TableField(value = "address")
    @Size(max = 255, message = "地址字段过长")
    private String address;

    /**
     * 到期日期
     */
    @Schema(description = "到期日期")
    @TableField(value = "expire_date")
    private String expireDate;

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
