package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.TenantDbConfigDto;
import com.kgplatform.system.domain.po.TenantDbConfig;
import com.kgplatform.system.domain.vo.TenantDbConfigVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

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
}
