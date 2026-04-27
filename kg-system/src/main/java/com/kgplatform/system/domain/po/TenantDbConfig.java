package com.kgplatform.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgplatform.common.core.domain.BaseAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 租户数据库配置
 * <p>
 * TenantDbConfig表实体类
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
     * 数据库类型
     */
    @Schema(description = "数据库类型")
    @TableField(value = "db_type")
    @Size(max = 20, message = "数据库类型字段过长")
    private String dbType;

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
     * 加密密码
     */
    @Schema(description = "加密密码")
    @TableField(value = "db_password_encrypted")
    @Size(max = 512, message = "加密密码字段过长")
    private String dbPasswordEncrypted;

    /**
     * JDBC参数
     */
    @Schema(description = "JDBC参数")
    @TableField(value = "jdbc_params")
    @Size(max = 500, message = "JDBC参数字段过长")
    private String jdbcParams;

    /**
     * 子库版本
     */
    @Schema(description = "子库版本")
    @TableField(value = "schema_version")
    @Size(max = 32, message = "子库版本字段过长")
    private String schemaVersion;

    /**
     * 数据库状态
     */
    @Schema(description = "数据库状态")
    @TableField(value = "db_status")
    @Size(max = 20, message = "数据库状态字段过长")
    private String dbStatus;

    /**
     * 最近检查时间
     */
    @Schema(description = "最近检查时间")
    @TableField(value = "last_check_time")
    private LocalDateTime lastCheckTime;

    /**
     * 最近检查结果
     */
    @Schema(description = "最近检查结果")
    @TableField(value = "last_check_result")
    @Size(max = 500, message = "最近检查结果字段过长")
    private String lastCheckResult;

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
