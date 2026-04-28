package com.kgplatform.common.datasource.routing;

import com.kgplatform.common.datasource.context.TenantContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TenantRoutingDataSourceTest {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void should_return_master_data_source_when_tenant_absent() {
        DataSource masterDataSource = mock(DataSource.class);
        TenantDataSourceDefinitionProvider provider = mock(TenantDataSourceDefinitionProvider.class);
        TenantRoutingDataSource routingDataSource = new TestableTenantRoutingDataSource(masterDataSource, provider);

        assertSame(masterDataSource, routingDataSource.resolveDataSource(null));
    }

    @Test
    void should_cache_tenant_data_source() {
        DataSource masterDataSource = mock(DataSource.class);
        TenantDataSourceDefinitionProvider provider = mock(TenantDataSourceDefinitionProvider.class);
        TenantRoutingDataSource routingDataSource = new TestableTenantRoutingDataSource(masterDataSource, provider);

        TenantDataSourceDefinition definition = new TenantDataSourceDefinition(
                1L,
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://127.0.0.1:3306/tenant_test?useUnicode=true",
                "root",
                "secret"
        );
        when(provider.load(1L)).thenReturn(definition);

        DataSource first = routingDataSource.resolveDataSource(1L);
        DataSource second = routingDataSource.resolveDataSource(1L);

        assertSame(first, second);
        verify(provider, times(1)).load(1L);
    }

    private static class TestableTenantRoutingDataSource extends TenantRoutingDataSource {
        TestableTenantRoutingDataSource(DataSource masterDataSource,
                                        TenantDataSourceDefinitionProvider definitionProvider) {
            super(masterDataSource, definitionProvider);
        }
    }
}
