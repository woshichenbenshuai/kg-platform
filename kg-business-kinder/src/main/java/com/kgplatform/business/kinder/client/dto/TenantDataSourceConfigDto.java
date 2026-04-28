package com.kgplatform.business.kinder.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 租户数据源配置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantDataSourceConfigDto implements Serializable {

    private Long tenantId;

    private String dbHost;

    private Integer dbPort;

    private String dbName;

    private String dbUsername;

    private String dbPasswordEncrypted;

    private String jdbcParams;
}
