package com.kgplatform.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kgplatform.auth.domain.po.AuthUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * AuthUserMapper
 * <p>
 * AuthUserMapper数据访问接口层
 *
 * @author kg_chen
 * @since 2026-04-22 18:50:54
 */
@Mapper
public interface AuthUserMapper extends BaseMapper<AuthUser> {

    AuthUser selectByUsername(@Param("username") String username);
}
