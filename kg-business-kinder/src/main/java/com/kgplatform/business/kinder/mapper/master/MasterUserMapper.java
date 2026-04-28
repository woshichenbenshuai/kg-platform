package com.kgplatform.business.kinder.mapper.master;

import org.apache.ibatis.annotations.Param;

/**
 * 主库用户 Mapper
 */
public interface MasterUserMapper {

    String selectEnabledNicknameByUserId(@Param("userId") Long userId);
}
