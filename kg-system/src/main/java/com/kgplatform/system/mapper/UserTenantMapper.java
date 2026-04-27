package com.kgplatform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kgplatform.system.domain.dto.UserTenantDto;
import com.kgplatform.system.domain.po.UserTenant;
import com.kgplatform.system.domain.vo.UserTenantVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
/**
 * ç¨æ·ç§æ·å
³ç³» Mapper æ¥å£
 * <p>
 * UserTenantMapper Mapper æ¥å£
 *
 * @author kg_chen
 * @since 2026-04-27 17:41:45
 */

@Mapper
@Component
public interface UserTenantMapper extends BaseMapper<UserTenant> {

    /**
     * 分页查询用户租户关系
Â³Ã§Â³Â»
     *
     * @param page 分页参数
     * @param vo 查询条件
     * @return 分页结果
     */
    Page<UserTenantDto> selectPageList(Page<UserTenantDto> page, @Param("vo") UserTenantVo vo);
}
