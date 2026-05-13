package com.kgplatform.business.kinder.datasource;

import com.kgplatform.business.kinder.client.TenantDataSourceConfigClient;
import com.kgplatform.business.kinder.client.dto.TenantDataSourceConfigDto;
import com.kgplatform.common.datasource.routing.TenantDataSourceDefinition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MasterTenantDataSourceDefinitionProviderTest {

    @Test
    void should_build_mysql_definition_from_master_config() {
        TenantDataSourceConfigClient client = mock(TenantDataSourceConfigClient.class);
        when(client.getConfigByTenantId(1L)).thenReturn(new TenantDataSourceConfigDto()
                .setTenantId(1L)
                .setDbHost("127.0.0.1")
                .setDbPort(3306)
                .setDbName("tenant_db")
                .setDbUsername("root")
                .setDbPassword("secret"));

        MasterTenantDataSourceDefinitionProvider provider = new MasterTenantDataSourceDefinitionProvider(client);
        TenantDataSourceDefinition definition = provider.load(1L);

        assertEquals(1L, definition.getTenantId());
        assertEquals("com.mysql.cj.jdbc.Driver", definition.getDriverClassName());
        assertEquals("jdbc:mysql://127.0.0.1:3306/tenant_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai", definition.getUrl());
        assertEquals("root", definition.getUsername());
        assertEquals("secret", definition.getPassword());
    }
}
