package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.TenantDbConfigDto;
import com.kgplatform.system.domain.po.TenantDbConfig;
import com.kgplatform.system.domain.vo.TenantDbConfigVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 租户数据库配置
 * <p>
 * TenantDbConfig表数据库访问层
 *
 * @author kg_chen
 * @since 2026-04-24 09:10:00
 */
@Mapper
@Component
public interface TenantDbConfigMapper extends BaseMapper<TenantDbConfig> {

    /**
     * 查询列表
     *
     * @param page 分页
     * @param vo   入参
     * @return 结果
     */
    Page<TenantDbConfigDto> selectPageList(Page<TenantDbConfigDto> page,
                                           @Param("vo") TenantDbConfigVo vo);

    /**
     * 按租户查询数据库配置，包含逻辑删除数据。
     *
     * @param tenantId 租户主键
     * @return 配置列表
     */
    List<TenantDbConfig> selectAllByTenantId(@Param("tenantId") Long tenantId);

    /**
     * 更新自动开库配置，绕过逻辑删除过滤。
     *
     * @param entity 配置
     * @return 更新行数
     */
    int updateProvisioningConfig(TenantDbConfig entity);
}
