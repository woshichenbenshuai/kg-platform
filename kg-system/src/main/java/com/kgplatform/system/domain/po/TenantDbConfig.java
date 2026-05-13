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
 * 租户数据库配置
 * <p>
 * TenantDbConfig实体类
 *
 * @author kg_chen
 * @since 2026-04-24 09:10:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("tenant_db_config")
@Schema(description = "租户数据库配置")
@EqualsAndHashCode(callSuper = true)
public class TenantDbConfig extends BaseAuditingEntity<Long> implements Serializable {

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    @TableField(value = "tenant_id")
    private Long tenantId;

    /**
     * 数据库主机
     */
    @Schema(description = "数据库主机")
    @TableField(value = "db_host")
    @Size(max = 128, message = "数据库主机字段过长")
    private String dbHost;

    /**
     * 数据库端口
     */
    @Schema(description = "数据库端口")
    @TableField(value = "db_port")
    private Integer dbPort;

    /**
     * 数据库名称
     */
    @Schema(description = "数据库名称")
    @TableField(value = "db_name")
    @Size(max = 128, message = "数据库名称字段过长")
    private String dbName;

    /**
     * 数据库用户名
     */
    @Schema(description = "数据库用户名")
    @TableField(value = "db_username")
    @Size(max = 128, message = "数据库用户名字段过长")
    private String dbUsername;

    /**
     * 数据库密码
     */
    @Schema(description = "数据库密码")
    @TableField(value = "db_password")
    @Size(max = 512, message = "数据库密码字段过长")
    private String dbPassword;

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
