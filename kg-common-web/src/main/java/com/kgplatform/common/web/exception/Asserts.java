package com.kgplatform.common.web.exception;


import com.kgplatform.common.web.core.IEnum;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
/**
 * 断言处理类，用于抛出各种API异常
 *
 * @author chen
 * @since 2026-04-23 17:44:16
 */

public final class Asserts {
    private Asserts() {
    }

    /**
     * 断言该方法会报错
     *
     * @param message 报错信息
     * @throws ApiException 自定义异常
     */
    public static void fail(String message) {
        throw new ApiException(message);
    }

    /**
     * 断言该方法会报错
     *
     * @param status 错误枚举
     * @throws ApiException 自定义异常
     */
    public static void fail(IEnum<String> status) {
        throw new ApiException(status);
    }

    /**
     * 断言该方法表达式为true，如果false会报错
     *
     * @param expression bool表达式
     * @param status     错误枚举
     * @throws ApiException 自定义异常
     */
    public static void isTrue(boolean expression, IEnum<String> status) {
        if (!expression) {
            throw new ApiException(status);
        }
    }

    /**
     * 断言该方法表达式为true，如果false会报错
     *
     * @param expression bool表达式
     * @param message    错误信息
     * @throws ApiException 自定义异常
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言该对象不能为null，如果为null会报错
     *
     * @param object {@link Object} 对象
     * @param status {@link IEnum} 错误枚举
     * @throws ApiException 自定义异常
     */
    public static void notNull(Object object, IEnum<String> status) {
        if (Objects.isNull(object)) {
            throw new ApiException(status);
        }
    }

    /**
     * 断言该对象不能为null，如果为null会报错
     *
     * @param object  {@link Object} 对象
     * @param message 错误信息
     * @throws ApiException 自定义异常
     */
    public static void notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言该对象为null，如果不为null会报错
     *
     * @param object {@link Object} 对象
     * @param status {@link IEnum} 错误枚举
     * @throws ApiException 自定义异常
     */
    public static void isNull(Object object, IEnum<String> status) {
        if (!Objects.isNull(object)) {
            throw new ApiException(status);
        }
    }

    /**
     * 断言该对象为null，如果不为null会报错
     *
     * @param object  {@link Object} 对象
     * @param message 错误信息
     * @throws ApiException 自定义异常
     */
    public static void isNull(Object object, String message) {
        if (!Objects.isNull(object)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言该字符串为空(空字符串或者null),如果不为空则报错
     *
     * @param str    字符串
     * @param status {@link IEnum} 错误枚举
     * @throws ApiException 自定义异常
     */
    public static void isBlank(String str, IEnum<String> status) {
        if (!StringUtils.isBlank(str)) {
            throw new ApiException(status);
        }
    }

    /**
     * 断言该字符串为空(空字符串或者null),如果不为空则报错
     *
     * @param str     字符串
     * @param message 错误信息
     * @throws ApiException 自定义异常
     */
    public static void isBlank(String str, String message) {
        if (!StringUtils.isBlank(str)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言该字符串不为空(空字符串或者null),如果为空则报错
     *
     * @param str    字符串
     * @param status {@link IEnum} 错误枚举
     * @throws ApiException 自定义异常
     */
    public static void notBlank(String str, IEnum<String> status) {
        if (StringUtils.isBlank(str)) {
            throw new ApiException(status);
        }
    }

    /**
     * 断言该字符串不为空(空字符串或者null),如果为空则报错
     *
     * @param str     字符串
     * @param message 错误信息
     * @throws ApiException 自定义异常
     */
    public static void notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言数组对象为空(null或者length=0),如果不为空则报错
     *
     * @param objs   数组对象
     * @param status 错误枚举类
     * @throws ApiException 自定义异常
     */
    public static void isEmpty(Object[] objs, IEnum<String> status) {
        if (!ArrayUtils.isEmpty(objs)) {
            throw new ApiException(status);
        }
    }

    /**
     * 断言数组对象为空(null或者length=0),如果不为空则报错
     *
     * @param objs    数组对象
     * @param message 错误信息
     * @throws ApiException 自定义异常
     */
    public static void isEmpty(Object[] objs, String message) {
        if (!ArrayUtils.isEmpty(objs)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言集合对象为空(null或者length=0),如果不为空则报错
     *
     * @param objs   集合对象
     * @param status 错误枚举类
     * @throws ApiException 自定义异常
     */
    public static void isEmpty(Collection<?> objs, IEnum<String> status) {
        if (!CollectionUtils.isEmpty(objs)) {
            throw new ApiException(status);
        }
    }


    /**
     * 断言集合对象为空(null或者size=0),如果不为空则报错
     *
     * @param objs    集合对象
     * @param message 错误信息
     * @throws ApiException 自定义异常
     */
    public static void isEmpty(Collection<?> objs, String message) {
        if (!CollectionUtils.isEmpty(objs)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言Map对象为空(null或者length=0),如果不为空则报错
     *
     * @param objs   Map对象
     * @param status 错误枚举类
     * @throws ApiException 自定义异常
     */
    public static void isEmpty(Map<?, ?> objs, IEnum<String> status) {
        if (!CollectionUtils.isEmpty(objs)) {
            throw new ApiException(status);
        }
    }

    /**
     * 断言Map对象为空(null或者size=0),如果不为空则报错
     *
     * @param objs    Map对象
     * @param message 错误信息
     * @throws ApiException 自定义异常
     */
    public static void isEmpty(Map<?, ?> objs, String message) {
        if (!CollectionUtils.isEmpty(objs)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言数组对象不为空(null或者length=0),如果为空则报错
     *
     * @param objs   数组对象
     * @param status 错误枚举类
     * @throws ApiException 自定义异常
     */
    public static void notEmpty(Object[] objs, IEnum<String> status) {
        if (ArrayUtils.isEmpty(objs)) {
            throw new ApiException(status);
        }
    }

    /**
     * 断言数组对象不为空(null或者length=0),如果为空则报错
     *
     * @param objs    数组对象
     * @param message 错误信息
     * @throws ApiException 自定义异常
     */
    public static void notEmpty(Object[] objs, String message) {
        if (ArrayUtils.isEmpty(objs)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言集合对象不为空(null或者length=0),如果为空则报错
     *
     * @param objs   集合对象
     * @param status 错误枚举类
     * @throws ApiException 自定义异常
     */
    public static void notEmpty(Collection<?> objs, IEnum<String> status) {
        if (CollectionUtils.isEmpty(objs)) {
            throw new ApiException(status);
        }
    }

    /**
     * 断言集合对象不为空(null或者size=0),如果为空则报错
     *
     * @param objs    集合对象
     * @param message 错误信息
     * @throws ApiException 自定义异常
     */
    public static void notEmpty(Collection<?> objs, String message) {
        if (CollectionUtils.isEmpty(objs)) {
            throw new ApiException(message);
        }
    }

    /**
     * 断言Map对象不为空(null或者size=0),如果为空则报错
     *
     * @param objs   集合对象
     * @param status 错误枚举
     * @throws ApiException 自定义异常
     */
    public static void notEmpty(Map<?, ?> objs, IEnum<String> status) {
        if (CollectionUtils.isEmpty(objs)) {
            throw new ApiException(status);
        }
    }

    /**
     * 断言Map对象不为空(null或者size=0),如果为空则报错
     *
     * @param objs    集合对象
     * @param message 错误信息
     * @throws ApiException 自定义异常
     */
    public static void notEmpty(Map<?, ?> objs, String message) {
        if (CollectionUtils.isEmpty(objs)) {
            throw new ApiException(message);
        }
    }
}

