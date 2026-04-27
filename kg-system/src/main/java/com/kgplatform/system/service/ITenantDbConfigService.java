package com.kgplatform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kgplatform.system.domain.dto.TenantDbConfigDto;
import com.kgplatform.system.domain.po.TenantDbConfig;
import com.kgplatform.system.domain.vo.TenantDbConfigVo;

/**
 * 租户数据库配置 Service 接口
 * <p>
 * ITenantDbConfigService Service 接口
 *
 * @author kg_chen
 * @since 2026-04-24 09:10:00
 */
public interface ITenantDbConfigService extends IService<TenantDbConfig> {

    /**
     * 分页查询所有数据
     *
     * @param current 当前页码，默认0
     * @param size    每页多少条，默认10
     * @param vo      查询入参
     * @return 所有数据
     */
    Page<TenantDbConfigDto> selectPage(Integer current, Integer size, TenantDbConfigVo vo);

    /**
     * 新增租户数据库配置
     *
     * @param vo 入参
     * @return 新增结果
     */
    boolean saveTenantDbConfig(TenantDbConfigVo vo);

    /**
     * 修改租户数据库配置
     *
     * @param vo 修改条件
     * @return 修改结果
     */
    Boolean update(TenantDbConfigVo vo);

    /**
     * 删除租户数据库配置
     *
     * @param id 主键
     * @return 删除结果
     */
    boolean delete(Long id);

    /**
     * 测试数据库连接
     *
     * @param vo 入参
     * @return 结果
     */
    String testConnection(TenantDbConfigVo vo);

    /**
     * 查询子库版本
     *
     * @param tenantId 租户ID
     * @return 版本信息
     */
    String getSchemaVersion(Long tenantId);
}
