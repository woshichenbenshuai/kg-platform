package com.kgplatform.system.service;

import com.kgplatform.system.domain.dto.CurrentUserAccessDto;

/**
 * 当前用户访问聚合 Service 接口
 *
 * @author kg_chen
 * @since 2026-04-27 17:26:26
 */
public interface ICurrentUserAccessService {

    /**
     * 查询当前用户访问聚合
     *
     * @param userId 用户主键
     * @return 当前用户访问聚合
     */
    CurrentUserAccessDto getCurrentUserAccess(Long userId);
}
