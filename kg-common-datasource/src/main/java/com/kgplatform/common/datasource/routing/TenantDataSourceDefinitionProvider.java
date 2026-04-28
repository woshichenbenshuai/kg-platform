package com.kgplatform.common.datasource.routing;

/**
 * 租户数据源定义提供器
 */
public interface TenantDataSourceDefinitionProvider {

    TenantDataSourceDefinition load(Long tenantId);
}
