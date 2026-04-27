package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.TenantDto;
import com.kgplatform.system.domain.po.Tenant;
import com.kgplatform.system.domain.vo.TenantVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
/**
 * 系统租户 Mapper 接口
 * <p>
 * TenantMapper Mapper 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Mapper
@Component
public interface TenantMapper extends BaseMapper<Tenant> {

    /**
     * 分页查询系统租户
     *
     * @param page 分页参数
     * @param vo 查询条件
     * @return 分页结果
     */
    Page<TenantDto> selectPageList(Page<TenantDto> page, @Param("vo") TenantVo vo);
}
