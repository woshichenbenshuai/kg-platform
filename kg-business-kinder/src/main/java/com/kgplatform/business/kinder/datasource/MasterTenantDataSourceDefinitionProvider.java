package com.kgplatform.business.kinder.datasource;

import com.kgplatform.business.kinder.client.TenantDataSourceConfigClient;
import com.kgplatform.business.kinder.client.dto.TenantDataSourceConfigDto;
import com.kgplatform.common.datasource.routing.TenantDataSourceDefinition;
import com.kgplatform.common.datasource.routing.TenantDataSourceDefinitionProvider;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Component;

/**
 * Loads tenant datasource definitions from the master database.
 */
@Component
public class MasterTenantDataSourceDefinitionProvider implements TenantDataSourceDefinitionProvider {

    private static final String DEFAULT_JDBC_PARAMS = "useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";

    private final TenantDataSourceConfigClient tenantDataSourceConfigClient;

    public MasterTenantDataSourceDefinitionProvider(TenantDataSourceConfigClient tenantDataSourceConfigClient) {
        this.tenantDataSourceConfigClient = tenantDataSourceConfigClient;
    }

    @Override
    public TenantDataSourceDefinition load(Long tenantId) {
        Asserts.notNull(tenantId, "租户主键不能为空");
        TenantDataSourceConfigDto config = tenantDataSourceConfigClient.getConfigByTenantId(tenantId);
        Asserts.notNull(config, "租户数据库配置不存在");

        String url = "jdbc:mysql://" + config.getDbHost() + ":" + config.getDbPort()
                + "/" + config.getDbName() + "?" + DEFAULT_JDBC_PARAMS;

        return new TenantDataSourceDefinition(
                tenantId,
                "com.mysql.cj.jdbc.Driver",
                url,
                config.getDbUsername(),
                config.getDbPassword()
        );
    }
}
