package com.kgplatform.common.web.core;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * IEnum
 *
 * @author chen
 * @since 2026-04-23 17:05:42
 */
public interface IEnum<T extends Serializable> {
    /**
     * 枚举值
     *
     * @return 枚举值
     */
    @JsonValue
    T getCode();

    /**
     * 枚举值描述
     *
     * @return 描述信息
     */
    String getMsg();

    /**
     * 根据枚举值返回枚举实例
     *
     * @param code 枚举值
     * @return 枚举实例
     */
    IEnum<T> valueOfCode(T code);
}
