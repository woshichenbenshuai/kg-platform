package com.kgplatform.common.datasource.routing;

/**
 * 租户数据源定义
 */
public class TenantDataSourceDefinition {

    private final Long tenantId;
    private final String driverClassName;
    private final String url;
    private final String username;
    private final String password;

    public TenantDataSourceDefinition(Long tenantId,
                                      String driverClassName,
                                      String url,
                                      String username,
                                      String password) {
        this.tenantId = tenantId;
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
