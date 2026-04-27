package com.kgplatform.common.web.exception;


import com.kgplatform.common.web.core.Status;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author : Wuyibo
 * @since : 2020/7/21 17:09
 */
public interface Problem {

    /**
     * toString
     *
     * @param problem 问题
     * @return string
     */
    static String toString(final Problem problem) {
        return StringUtils.join(
                ObjectUtils.defaultIfNull(problem.getCode(), Status.INTERNAL_SERVER_ERROR.getCode()), ":",
                problem.getMessage());
    }

    /**
     * 状态码
     *
     * @return 状态码
     */
    default String getCode() {
        return Status.BAD_REQUEST.getCode();
    }

    /**
     * 消息
     *
     * @return 消息
     */
    default String getMessage() {
        return null;
    }
}
