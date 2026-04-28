package com.kgplatform.business.kinder.datasource;

import com.kgplatform.business.kinder.client.TenantDataSourceConfigClient;
import com.kgplatform.business.kinder.client.dto.TenantDataSourceConfigDto;
import com.kgplatform.common.datasource.routing.TenantDataSourceDefinition;
import com.kgplatform.common.datasource.routing.TenantDataSourceDefinitionProvider;
import com.kgplatform.common.web.exception.Asserts;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 通过 Client 读取租户数据源定义
 */
@Component
public class MasterTenantDataSourceDefinitionProvider implements TenantDataSourceDefinitionProvider {

    private final TenantDataSourceConfigClient tenantDataSourceConfigClient;

    public MasterTenantDataSourceDefinitionProvider(TenantDataSourceConfigClient tenantDataSourceConfigClient) {
        this.tenantDataSourceConfigClient = tenantDataSourceConfigClient;
    }

    @Override
    public TenantDataSourceDefinition load(Long tenantId) {
        Asserts.notNull(tenantId, "租户主键不能为空");
        TenantDataSourceConfigDto config = tenantDataSourceConfigClient.getConfigByTenantId(tenantId);
        Asserts.notNull(config, "租户数据库配置不存在");

        String jdbcParams = defaultJdbcParams(config.getJdbcParams());
        String url = "jdbc:mysql://" + config.getDbHost() + ":" + config.getDbPort()
                + "/" + config.getDbName() + "?" + jdbcParams;

        return new TenantDataSourceDefinition(
                tenantId,
                "com.mysql.cj.jdbc.Driver",
                url,
                config.getDbUsername(),
                decryptPassword(config.getDbPasswordEncrypted())
        );
    }

    private String defaultJdbcParams(String jdbcParams) {
        return jdbcParams == null || jdbcParams.isBlank()
                ? "useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
                : jdbcParams;
    }

    private String decryptPassword(String encryptedPassword) {
        if (encryptedPassword == null || encryptedPassword.isBlank()) {
            return encryptedPassword;
        }
        if (encryptedPassword.startsWith("ENC(") && encryptedPassword.endsWith(")")) {
            return encryptedPassword.substring(4, encryptedPassword.length() - 1);
        }
        return new String(Base64.getDecoder().decode(encryptedPassword), StandardCharsets.UTF_8);
    }
}
