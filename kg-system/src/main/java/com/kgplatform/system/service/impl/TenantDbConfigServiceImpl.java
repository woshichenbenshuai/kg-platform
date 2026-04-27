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
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * 租户数据库配置
 * <p>
 * TenantDbConfig表服务实现类
 *
 * @author kg_chen
 * @since 2026-04-24 09:10:00
 */
@Service
public class TenantDbConfigServiceImpl extends ServiceImpl<TenantDbConfigMapper, TenantDbConfig> implements ITenantDbConfigService {

    @Override
    public Page<TenantDbConfigDto> selectPage(Integer current, Integer size, TenantDbConfigVo vo) {
        return baseMapper.selectPageList(new Page<>(current, size), vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveTenantDbConfig(TenantDbConfigVo vo) {
        Asserts.notNull(vo, "数据库配置参数不能为空");
        Asserts.notNull(vo.getBindTenantId(), "租户主键不能为空");
        Asserts.notBlank(vo.getDbType(), "数据库类型不能为空");
        Asserts.notBlank(vo.getDbHost(), "数据库主机不能为空");
        Asserts.notNull(vo.getDbPort(), "数据库端口不能为空");
        Asserts.notBlank(vo.getDbName(), "数据库名称不能为空");
        Asserts.notBlank(vo.getDbUsername(), "数据库用户名不能为空");
        Asserts.notBlank(vo.getDbPassword(), "数据库密码不能为空");
        long count = count(new LambdaQueryWrapper<TenantDbConfig>()
                .eq(TenantDbConfig::getTenantId, vo.getBindTenantId())
                .eq(TenantDbConfig::getDeleteStatus, Boolean.FALSE));
        Asserts.isTrue(count == 0, "该租户数据库配置已存在");

        TenantDbConfig entity = TenantDbConfigConverter.INSTANCE.vo2Domain(vo);
        entity.setDbPasswordEncrypted(encryptPassword(vo.getDbPassword()));
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
            entity.setDbPasswordEncrypted(old.getDbPasswordEncrypted());
        } else {
            entity.setDbPasswordEncrypted(encryptPassword(vo.getDbPassword()));
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
    @Transactional(rollbackFor = Exception.class)
    public String testConnection(TenantDbConfigVo vo) {
        Asserts.notNull(vo, "数据库配置参数不能为空");
        Asserts.notBlank(vo.getDbHost(), "数据库主机不能为空");
        Asserts.notNull(vo.getDbPort(), "数据库端口不能为空");
        Asserts.notBlank(vo.getDbName(), "数据库名称不能为空");
        Asserts.notBlank(vo.getDbUsername(), "数据库用户名不能为空");

        String password = vo.getDbPassword();
        if ((password == null || password.isBlank()) && vo.getId() != null) {
            TenantDbConfig old = getById(vo.getId());
            Asserts.notNull(old, "数据库配置不存在");
            password = decryptPassword(old.getDbPasswordEncrypted());
        }
        Asserts.notBlank(password, "数据库密码不能为空");

        String url = buildJdbcUrl(vo.getDbHost(), vo.getDbPort(), vo.getDbName(), vo.getJdbcParams());
        String result;
        String status;
        try (Connection ignored = DriverManager.getConnection(url, vo.getDbUsername(), password)) {
            result = "连接成功";
            status = "NORMAL";
        } catch (Exception e) {
            result = e.getMessage() == null ? "连接失败" : e.getMessage();
            status = "ERROR";
        }

        if (vo.getId() != null) {
            update(new LambdaUpdateWrapper<TenantDbConfig>()
                    .eq(TenantDbConfig::getId, vo.getId())
                    .set(TenantDbConfig::getDbStatus, status)
                    .set(TenantDbConfig::getLastCheckTime, LocalDateTime.now())
                    .set(TenantDbConfig::getLastCheckResult, result));
        }
        return result;
    }

    @Override
    public String getSchemaVersion(Long tenantId) {
        Asserts.notNull(tenantId, "租户主键不能为空");
        TenantDbConfig entity = getOne(new LambdaQueryWrapper<TenantDbConfig>()
                .eq(TenantDbConfig::getTenantId, tenantId)
                .eq(TenantDbConfig::getDeleteStatus, Boolean.FALSE));
        Asserts.notNull(entity, "数据库配置不存在");

        String url = buildJdbcUrl(entity.getDbHost(), entity.getDbPort(), entity.getDbName(), entity.getJdbcParams());
        try (Connection connection = DriverManager.getConnection(url, entity.getDbUsername(), decryptPassword(entity.getDbPasswordEncrypted()));
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

    private String buildJdbcUrl(String host, Integer port, String dbName, String jdbcParams) {
        String params = (jdbcParams == null || jdbcParams.isBlank())
                ? "useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
                : jdbcParams;
        return "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?" + params;
    }

    private String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    private String decryptPassword(String encryptedPassword) {
        if (encryptedPassword == null || encryptedPassword.isBlank()) {
            return encryptedPassword;
        }
        if (encryptedPassword.startsWith("ENC(") && encryptedPassword.endsWith(")")) {
            return encryptedPassword.substring(4, encryptedPassword.length() - 1);
        }
        return new String(Base64.getDecoder().decode(encryptedPassword));
    }
}
