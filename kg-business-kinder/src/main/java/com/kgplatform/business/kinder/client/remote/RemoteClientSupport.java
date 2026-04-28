package com.kgplatform.business.kinder.client.remote;

import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.exception.Asserts;

/**
 * 远程调用结果支持
 */
public abstract class RemoteClientSupport {

    protected <T> T unwrap(Result<T> result, String emptyMessage) {
        Asserts.notNull(result, "远程服务返回为空");
        Asserts.isTrue("0".equals(result.getCode()), result.getMsg() == null ? "远程服务调用失败" : result.getMsg());
        T data = result.getData();
        Asserts.notNull(data, emptyMessage);
        return data;
    }
}
