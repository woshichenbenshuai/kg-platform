package com.kgplatform.system.service;

import com.kgplatform.system.domain.po.Tenant;

/**
 * 租户业务库自动开通服务。
 */
public interface TenantDatabaseProvisioner {

    /**
     * 为幼儿园创建租户业务库、导入模板并写入主库数据库配置。
     *
     * @param tenant 租户
     * @return 生成的数据库名称
     */
    String rebuild(Tenant tenant);
}
