package com.kgplatform.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 租户数据库配置
 * <p>
 * TenantDbConfigDto返回对象
 *
 * @author kg_chen
 * @since 2026-04-24 09:10:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "租户数据库配置")
@EqualsAndHashCode(callSuper = false)
public class TenantDbConfigDto implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tenantId;

    private String dbHost;
    private Integer dbPort;
    private String dbName;
    private String dbUsername;
    private String dbPassword;
    private Boolean status;
    private Boolean deleteStatus;
    private LocalDateTime createTime;
}
