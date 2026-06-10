package com.kgplatform.business.kinder.service;

import java.util.Map;

/**
 * 园长端门户服务
 * <p>
 * PrincipalPortalService 服务接口
 *
 * @author kg_chen
 * @since 2026-06-10 00:00:00
 */
public interface PrincipalPortalService {

    /**
     * 查询园长端首页概览
     *
     * @return 首页概览数据
     */
    Map<String, Object> home();
}
