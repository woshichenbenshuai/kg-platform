package com.kgplatform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kgplatform.common.web.exception.Asserts;
import com.kgplatform.system.domain.convert.TenantDbConfigConverter;
import com.kgplatform.system.domain.dto.TenantDbConfigDto;
import com.kgplatform.system.domain.po.TenantDbConfig;
import com.kgplatform.system.domain.vo.TenantDbConfigVo;
import com.kgplatform.system.mapper.TenantDbConfigMapper;
import com.kgplatform.system.service.ITenantDbConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Tenant database config service.
 */
@Service
public class TenantDbConfigServiceImpl extends ServiceImpl<TenantDbConfigMapper, TenantDbConfig> implements ITenantDbConfigService {

    private static final String DEFAULT_JDBC_PARAMS = "useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";

    @Override
    public Page<TenantDbConfigDto> selectPage(Integer current, Integer size, TenantDbConfigVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveTenantDbConfig(TenantDbConfigVo vo) {
        validateRequired(vo, true);
        long count = count(new LambdaQueryWrapper<TenantDbConfig>()
                .eq(TenantDbConfig::getTenantId, vo.getBindTenantId())
                .eq(TenantDbConfig::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "该租户数据库配置已存在");

        TenantDbConfig entity = TenantDbConfigConverter.INSTANCE.vo2Domain(vo);
        if (entity.getStatus() == null) {
            entity.setStatus(Boolean.TRUE);
        }
        if (entity.getDeleteStatus() == null) {
            entity.setDeleteStatus(Boolean.FALSE);
        }
        return save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(TenantDbConfigVo vo) {
        Asserts.notNull(vo, "数据库配置参数不能为空");
        Asserts.notNull(vo.getId(), "主键不能为空");
        TenantDbConfig old = getById(vo.getId());
        Asserts.notNull(old, "数据库配置不存在");

        TenantDbConfig entity = TenantDbConfigConverter.INSTANCE.vo2Domain(vo);
        if (vo.getDbPassword() == null || vo.getDbPassword().isBlank()) {
            entity.setDbPassword(old.getDbPassword());
        }
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        Asserts.notNull(id, "主键不能为空");
        return update(new LambdaUpdateWrapper<TenantDbConfig>()
                .eq(TenantDbConfig::getId, id)
                .set(TenantDbConfig::getDeleteStatus, Boolean.TRUE));
    }

    @Override
    public String testConnection(TenantDbConfigVo vo) {
        validateConnectionFields(vo);

        String password = vo.getDbPassword();
        if ((password == null || password.isBlank()) && vo.getId() != null) {
            TenantDbConfig old = getById(vo.getId());
            Asserts.notNull(old, "数据库配置不存在");
            password = old.getDbPassword();
        }
        Asserts.notBlank(password, "数据库密码不能为空");

        String url = buildJdbcUrl(vo.getDbHost(), vo.getDbPort(), vo.getDbName());
        try (Connection ignored = DriverManager.getConnection(url, vo.getDbUsername(), password)) {
            return "连接成功";
        } catch (Exception e) {
            return e.getMessage() == null ? "连接失败" : e.getMessage();
        }
    }

    @Override
    public String getSchemaVersion(Long tenantId) {
        Asserts.notNull(tenantId, "租户主键不能为空");
        TenantDbConfig entity = getOne(new LambdaQueryWrapper<TenantDbConfig>()
                .eq(TenantDbConfig::getTenantId, tenantId)
                .eq(TenantDbConfig::getDeleteStatus, Boolean.FALSE));
        Asserts.notNull(entity, "数据库配置不存在");

        String url = buildJdbcUrl(entity.getDbHost(), entity.getDbPort(), entity.getDbName());
        try (Connection connection = DriverManager.getConnection(url, entity.getDbUsername(), entity.getDbPassword());
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT version FROM schema_version LIMIT 1");
            if (rs.next()) {
                return rs.getString(1);
            }
            return "未初始化版本信息";
        } catch (Exception e) {
            return "未初始化版本信息";
        }
    }

    private void validateRequired(TenantDbConfigVo vo, boolean requirePassword) {
        validateConnectionFields(vo);
        Asserts.notNull(vo.getBindTenantId(), "租户主键不能为空");
        if (requirePassword) {
            Asserts.notBlank(vo.getDbPassword(), "数据库密码不能为空");
        }
    }

    private void validateConnectionFields(TenantDbConfigVo vo) {
        Asserts.notNull(vo, "数据库配置参数不能为空");
        Asserts.notBlank(vo.getDbHost(), "数据库主机不能为空");
        Asserts.notNull(vo.getDbPort(), "数据库端口不能为空");
        Asserts.notBlank(vo.getDbName(), "数据库名称不能为空");
        Asserts.notBlank(vo.getDbUsername(), "数据库用户名不能为空");
    }

    private String buildJdbcUrl(String host, Integer port, String dbName) {
        return "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?" + DEFAULT_JDBC_PARAMS;
    }
}
