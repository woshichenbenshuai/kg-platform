package com.kgplatform.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgplatform.common.web.core.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 租户数据库配置
 * <p>
 * TenantDbConfigVo入参对象
¥åå¯¹è±¡
 *
 * @author kg_chen
 * @since 2026-04-24 09:10:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(description = "租户数据库配置")
@EqualsAndHashCode(callSuper = true)
public class TenantDbConfigVo extends BaseVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 绑定租户ID
     */
    @Schema(description = "绑定租户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bindTenantId;

    /**
     * 数据库类型
     */
    @Schema(description = "数据库类型")
    @Size(max = 20, message = "数据库类型字段过长")
    private String dbType;

    /**
     * 数据库主机
     */
    @Schema(description = "数据库主机")
    @Size(max = 128, message = "数据库主机字段过长")
    private String dbHost;

    /**
     * 数据库端口
     */
    @Schema(description = "数据库端口")
    private Integer dbPort;

    /**
     * 数据库名称
     */
    @Schema(description = "数据库名称")
    @Size(max = 128, message = "数据库名称字段过长")
    private String dbName;

    /**
     * 数据库用户名
     */
    @Schema(description = "数据库用户名")
    @Size(max = 128, message = "数据库用户名字段过长")
    private String dbUsername;

    /**
     * 数据库密码
     */
    @Schema(description = "数据库密码")
    @Size(max = 512, message = "数据库密码字段过长")
    private String dbPassword;

    /**
     * JDBC参数
     */
    @Schema(description = "JDBC参数")
    @Size(max = 500, message = "JDBC参数字段过长")
    private String jdbcParams;

    /**
     * 子库版本
     */
    @Schema(description = "子库版本")
    @Size(max = 32, message = "子库版本字段过长")
    private String schemaVersion;

    /**
     * 数据库状态
     */
    @Schema(description = "数据库状态")
    @Size(max = 20, message = "数据库状态字段过长")
    private String dbStatus;

    /**
     * 最近检查时间
     */
    @Schema(description = "最近检查时间")
    private LocalDateTime lastCheckTime;

    /**
     * 最近检查结果
     */
    @Schema(description = "最近检查结果")
    @Size(max = 500, message = "最近检查结果字段过长")
    private String lastCheckResult;

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
}
