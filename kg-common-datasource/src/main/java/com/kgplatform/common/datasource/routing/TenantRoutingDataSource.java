package com.kgplatform.common.datasource.routing;

import com.kgplatform.common.datasource.context.TenantContextHolder;
import com.kgplatform.common.web.exception.Asserts;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 按租户动态路由的数据源
 */
public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    private final DataSource masterDataSource;
    private final TenantDataSourceDefinitionProvider definitionProvider;
    private final Map<Long, DataSource> tenantDataSources = new ConcurrentHashMap<>();

    public TenantRoutingDataSource(DataSource masterDataSource,
                                   TenantDataSourceDefinitionProvider definitionProvider) {
        this.masterDataSource = masterDataSource;
        this.definitionProvider = definitionProvider;
        super.setTargetDataSources(new HashMap<>());
        super.setDefaultTargetDataSource(masterDataSource);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContextHolder.getTenantId();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId == null) {
            return masterDataSource;
        }
        return tenantDataSources.computeIfAbsent(tenantId, this::createTenantDataSource);
    }

    DataSource resolveDataSource(Long tenantId) {
        TenantContextHolder.setTenantId(tenantId);
        try {
            return determineTargetDataSource();
        } finally {
            TenantContextHolder.clear();
        }
    }

    private DataSource createTenantDataSource(Long tenantId) {
        TenantDataSourceDefinition definition = definitionProvider.load(tenantId);
        Asserts.notNull(definition, "未找到租户数据源配置");

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(definition.getDriverClassName());
        dataSource.setJdbcUrl(definition.getUrl());
        dataSource.setUsername(definition.getUsername());
        dataSource.setPassword(definition.getPassword());
        dataSource.setPoolName("tenant-" + tenantId);
        return dataSource;
    }
}
