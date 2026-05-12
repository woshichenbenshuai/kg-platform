package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kgplatform.common.web.exception.ApiException;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.po.Tenant;
import com.kgplatform.system.domain.po.TenantDbConfig;
import com.kgplatform.system.mapper.TenantDbConfigMapper;
import com.kgplatform.system.mapper.TenantMapper;
import com.kgplatform.system.service.ITenantDbConfigService;
import com.kgplatform.system.service.TenantDatabaseProvisioner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 基于主库连接创建同 MySQL 实例下的幼儿园业务库。
 */
@Service
public class TenantDatabaseProvisionerImpl implements TenantDatabaseProvisioner {

    private static final String TEMPLATE_PATH = "db/tenant/kinder_schema_template.sql";
    private static final String DEFAULT_JDBC_PARAMS = "useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final Pattern SAFE_IDENTIFIER = Pattern.compile("[a-zA-Z0-9_]+");

    private final ITenantDbConfigService tenantDbConfigService;
    private final TenantDbConfigMapper tenantDbConfigMapper;
    private final TenantMapper tenantMapper;
    private final String masterUrl;
    private final String masterUsername;
    private final String masterPassword;

    public TenantDatabaseProvisionerImpl(ITenantDbConfigService tenantDbConfigService,
                                         TenantDbConfigMapper tenantDbConfigMapper,
                                         TenantMapper tenantMapper,
                                         @Value("${spring.datasource.url}") String masterUrl,
                                         @Value("${spring.datasource.username}") String masterUsername,
                                         @Value("${spring.datasource.password}") String masterPassword) {
        this.tenantDbConfigService = tenantDbConfigService;
        this.tenantDbConfigMapper = tenantDbConfigMapper;
        this.tenantMapper = tenantMapper;
        this.masterUrl = masterUrl;
        this.masterUsername = masterUsername;
        this.masterPassword = masterPassword;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public String rebuild(Tenant tenant) {
        Asserts.notNull(tenant, "租户不能为空");
        Asserts.notNull(tenant.getId(), "租户主键不能为空");

        JdbcInfo jdbcInfo = parseMasterJdbcInfo();
        TenantDbConfig existingConfig = getExistingConfig(tenant.getId());
        Asserts.isTrue(existingConfig == null, "幼儿园数据库已创建，不能重复生成或删除");
        String dbName = buildTenantDbName(tenant);

        assertTargetDatabaseNotReferenced(dbName, tenant.getId());
        createDatabase(jdbcInfo, dbName);
        importTemplate(jdbcInfo, dbName);
        saveOrUpdateDbConfig(existingConfig, tenant.getId(), jdbcInfo, dbName);
        return dbName;
    }

    private void createDatabase(JdbcInfo jdbcInfo, String dbName) {
        String adminUrl = buildJdbcUrl(jdbcInfo.host(), jdbcInfo.port(), null, jdbcInfo.params());
        try (Connection connection = DriverManager.getConnection(adminUrl, masterUsername, masterPassword);
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE DATABASE IF NOT EXISTS `" + dbName + "` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci");
        } catch (Exception e) {
            throw new ApiException("创建幼儿园业务库失败：" + e.getMessage());
        }
    }

    private void assertTargetDatabaseNotReferenced(String dbName, Long tenantId) {
        validateDatabaseName(dbName);
        long referencedByOtherTenant = tenantDbConfigService.count(Wrappers.<TenantDbConfig>lambdaQuery()
                .eq(TenantDbConfig::getDbName, dbName)
                .ne(TenantDbConfig::getTenantId, tenantId)
                .eq(TenantDbConfig::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(referencedByOtherTenant == 0, "目标数据库已被其他幼儿园使用");
    }

    private void importTemplate(JdbcInfo jdbcInfo, String dbName) {
        String tenantUrl = buildJdbcUrl(jdbcInfo.host(), jdbcInfo.port(), dbName, jdbcInfo.params());
        try (Connection connection = DriverManager.getConnection(tenantUrl, masterUsername, masterPassword)) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource(TEMPLATE_PATH));
        } catch (Exception e) {
            throw new ApiException("初始化幼儿园业务库模板失败：" + e.getMessage());
        }
    }

    private void saveOrUpdateDbConfig(TenantDbConfig existingConfig, Long tenantId, JdbcInfo jdbcInfo, String dbName) {
        TenantDbConfig entity = existingConfig == null ? new TenantDbConfig() : existingConfig;
        entity.setTenantId(tenantId);
        entity.setDbType("mysql");
        entity.setDbHost(jdbcInfo.host());
        entity.setDbPort(jdbcInfo.port());
        entity.setDbName(dbName);
        entity.setDbUsername(masterUsername);
        entity.setDbPasswordEncrypted(encryptPassword(masterPassword));
        entity.setJdbcParams(jdbcInfo.params());
        entity.setSchemaVersion("20260512.1");
        entity.setDbStatus("NORMAL");
        entity.setLastCheckTime(LocalDateTime.now());
        entity.setLastCheckResult("按模板重新生成业务库");
        entity.setStatus(Boolean.TRUE);
        entity.setDeleteStatus(Boolean.FALSE);
        if (entity.getId() == null) {
            tenantDbConfigService.save(entity);
        } else {
            tenantDbConfigMapper.updateProvisioningConfig(entity);
        }
    }

    private TenantDbConfig getExistingConfig(Long tenantId) {
        List<TenantDbConfig> configs = tenantDbConfigMapper.selectAllByTenantId(tenantId);
        return configs.isEmpty() ? null : configs.get(0);
    }

    private JdbcInfo parseMasterJdbcInfo() {
        try {
            String raw = masterUrl.substring("jdbc:".length());
            URI uri = new URI(raw);
            String host = uri.getHost();
            int port = uri.getPort() > 0 ? uri.getPort() : 3306;
            String params = uri.getQuery() == null || uri.getQuery().isBlank() ? DEFAULT_JDBC_PARAMS : uri.getQuery();
            Asserts.notBlank(host, "主库 JDBC 地址缺少主机");
            return new JdbcInfo(host, port, params);
        } catch (URISyntaxException | IllegalArgumentException e) {
            throw new ApiException("解析主库 JDBC 地址失败：" + e.getMessage());
        }
    }

    private String buildTenantDbName(Tenant tenant) {
        List<Tenant> tenants = tenantMapper.selectList(Wrappers.<Tenant>lambdaQuery()
                .eq(Tenant::getDeleteStatus, Boolean.FALSE));
        tenants.sort(Comparator
                .comparing(Tenant::getCreateTime, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(Tenant::getId, Comparator.nullsLast(Comparator.naturalOrder())));
        for (int i = 0; i < tenants.size(); i++) {
            if (Objects.equals(tenants.get(i).getId(), tenant.getId())) {
                return "kg_kinder_" + String.format("%08d", i + 1);
            }
        }
        throw new ApiException("租户不存在，无法生成业务库名称");
    }

    private void validateDatabaseName(String dbName) {
        Asserts.notBlank(dbName, "数据库名不能为空");
        Asserts.isTrue(dbName.startsWith("kg_kinder_"), "幼儿园业务库必须使用 kg_kinder_ 前缀");
        Asserts.isTrue(SAFE_IDENTIFIER.matcher(dbName).matches(), "数据库名不合法");
    }

    private String buildJdbcUrl(String host, int port, String dbName, String params) {
        String database = dbName == null || dbName.isBlank() ? "" : "/" + dbName;
        return "jdbc:mysql://" + host + ":" + port + database + "?" + params;
    }

    private String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    private record JdbcInfo(String host, int port, String params) {
    }
}
