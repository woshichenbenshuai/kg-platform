package com.kgplatform.common.web.core;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * REST API 返回结果
 *
 * @author : Wuyibo
 * @since : 2020/7/21 15:02
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Result<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 业务错误码
     */
    private String code;
    /**
     * 结果集
     */
    private T data;
    /**
     * 描述
     */
    private String msg;
    /**
     * 内部描述，不会序列化
     */
    @JsonIgnore
    private String internalMsg;


    /**
     * 默认返回成功
     *
     * @param data 返回的数据
     */
    public Result(T data) {
        this(data, Status.OK);
    }

    /**
     * 可自定义返回状态码和信息
     *
     * @param data 返回数据
     * @param msg  返回信息
     * @param code 状态码
     */
    public Result(T data, String msg, String code) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }

    /**
     * 自定义返回结果
     *
     * @param data   返回数据
     * @param status 返回状态
     * @see Status
     */
    public Result(T data, IEnum<String> status) {
        this(data, status.getMsg(), status.getCode());
    }

    /**
     * 可自定义返回状态码和信息
     *
     * @param data      返回数据
     * @param customMsg 自定义信息
     * @param status    状态码
     */
    public Result(T data, String customMsg, IEnum<String> status) {
        this(data, customMsg, status.getCode());
    }


    /**
     * 静态调用，返回成功结果
     *
     * @param <T>  数据类型
     * @param data 返回数据
     * @return 成功结果 e.g. {@code {code:0,msg:'成功',data:{}} }
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(data);
    }

    /**
     * 静态调用，返回成功
     *
     * @param <T> 数据类型
     * @return 成功结果 e.g. {@code {code:0,msg:'成功',data:{}} }
     */
    public static <T> Result<T> ok() {
        return new Result<>(null);
    }

    /**
     * 错误请求
     *
     * @param <T>  数据类型
     * @param msg  具体错误信息
     * @param code 400开头的错误码
     * @return 返回数据
     */
    public static <T> Result<T> badRequest(String msg, String code) {
        return new Result<>(null, msg, code);
    }

    /**
     * 错误请求
     *
     * @param <T>    数据类型
     * @param status 状态信息
     * @return 返回数据
     */
    public static <T> Result<T> badRequest(IEnum<String> status) {
        return new Result<>(null, status);
    }

    /**
     * 扩充详细信息
     *
     * @param status    状态码
     * @param customMsg 详细错误信息
     * @param <T>       数据类型
     * @return 返回错误信息
     */
    public static <T> Result<T> badRequest(IEnum<String> status, String customMsg) {
        return new Result<>(null, String.format("%s;%s", status.getMsg(), customMsg), status.getCode());
    }

    /**
     * 返回400错误信息
     *
     * @param <T> 数据类型
     * @return 返回400错误信息
     */
    public static <T> Result<T> badRequest() {
        return new Result<>(null, Status.BAD_REQUEST);
    }

    /**
     * 失败返回结果
     *
     * @param <T>  数据类型
     * @param data 数据
     * @return 错误信息
     */
    public static <T> Result<T> failedData(T data) {
        return new Result<>(data, Status.FAILED);
    }

    /**
     * 失败返回结果
     *
     * @param <T>       数据类型
     * @param data      数据
     * @param errorCode 错误枚举类
     * @return 错误信息
     */
    public static <T> Result<T> failedData(IEnum<String> errorCode, T data) {
        return new Result<>(data, errorCode);
    }

    /**
     * 失败返回结果
     *
     * @param <T>     数据类型
     * @param message 提示信息
     * @return 错误信息
     */
    public static <T> Result<T> failed(String message) {
        return new Result<>(null, message, Status.FAILED.getCode());
    }

    /**
     * 失败返回结果
     *
     * @param <T>    数据类型
     * @param failed 错误枚举
     * @return 错误信息
     */
    public static <T> Result<T> failed(IEnum<String> failed) {
        return new Result<>(null, failed);
    }

    /**
     * 失败返回结果
     *
     * @param <T> 数据类型
     * @return 错误信息
     */
    public static <T> Result<T> failed() {
        return failed(Status.FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param <T> 数据类型
     * @return 错误信息
     */
    public static <T> Result<T> validateFailed() {
        return failed(Status.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     * @param <T>     数据类型
     * @return 错误信息
     */
    public static <T> Result<T> validateFailed(String message) {
        return new Result<>(null, message, Status.VALIDATE_FAILED.getCode());
    }

    /**
     * 未登录返回结果
     *
     * @param <T>  数据类型
     * @param data 数据
     * @return 错误信息
     */
    public static <T> Result<T> unauthorized(T data) {
        return new Result<>(data, Status.UNAUTHORIZED);
    }

    /**
     * 未授权返回结果
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 错误信息
     */
    public static <T> Result<T> forbidden(T data) {
        return new Result<>(data, Status.FORBIDDEN);
    }

    /**
     * 判断是否成功
     *
     * @return 如果code是0，则返回true，否则false
     */
    @JsonIgnore
    public boolean isOk() {
        return Status.OK.getCode().equals(this.code);
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

    /**
     * 添加内部描述
     *
     * @param internalMsg 内部描述
     * @return 返回数据
     */
    public Result<T> internalMsg(String internalMsg) {
        this.internalMsg = internalMsg;
        return this;
    }
}
