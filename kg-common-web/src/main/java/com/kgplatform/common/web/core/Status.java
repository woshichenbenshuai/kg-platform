package com.kgplatform.common.web.core;


import one.util.streamex.StreamEx;

/**
 * Status
 *
 * @author chen
 * @since 2026-04-23 17:03:49
 */
public enum Status implements IEnum<String> {

    /**
     * 0 成功
     */
    OK("0", "成功"),
    /**
     * {@code 400 无效请求}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.1">HTTP/1.1: Semantics and Content, section 6.5.1</a>
     */
    BAD_REQUEST("400", "无效请求"),
    /**
     * 参数检验失败
     */
    VALIDATE_FAILED("400", "参数检验失败"),
    /**
     * {@code 48803 不合法的手机号}
     */
    BAD_REQ_MOBILE_ERROR("48803", "不合法的手机号"),
    /**
     * {@code 48804 不合法的验证码类型}
     */
    BAD_REQ_CODETYPE_ERROR("48804", "不合法的验证码类型"),
    /**
     * {@code 48805 密码设置不符}
     */
    BAD_REQ_PASSWORD_UNCONFORM("48805", "密码设置不符"),
    /**
     * {@code 48806 手机号未注册}
     */
    BAD_REQ_MOBILE_UNREGISTERED("48806", "手机号未注册"),
    /**
     * {@code 48807 手机号未激活}
     */
    BAD_REQ_MOBILE_UNINACTIVE("48807", "手机号未激活"),
    /**
     * {@code 48808 手机号已注册}
     */
    BAD_REQ_MOBILE_REGISTERED("48808", "手机号已注册"),
    /**
     * {@code 48809 密码错误}
     */
    BAD_REQ_PASSWORD_ERROR("48809", "密码错误"),
    /**
     * {@code 48810 注册失败}
     */
    BAD_REQ_REGISTER_FAIL("48810", "注册失败"),
    /**
     * {@code 48811 登录失败}
     */
    BAD_REQ_LOIN_FAIL("48811", "登录失败"),
    /**
     * {@code 48812 请完善账号信息}
     */
    BAD_REQ_PERFECT_ACCOUNTINFO("48812", "请完善账号信息"),
    /**
     * {@code 48813 请求超时}
     */
    BAD_REQ_TIMEOUT("48813", "请求超时"),
    /**
     * {@code 48814 未登录账号}
     */
    BAD_REQ_NO_LOGIN_ACCOUNT("48814", "未登录账号"),
    /**
     * {@code 48815 不合法的用户信息}
     */
    BAD_REQ_USERINFO_ERROR("48815", "不合法的用户信息"),
    /**
     * {@code 48816 不合法邮箱信息}
     */
    BAD_REQ_EMAIL_ERROR("48816", "不合法邮箱信息"),
    /**
     * {@code 48601 请获取验证码}
     */
    BAD_REQ_GET_CODE("48601", "请获取验证码"),
    /**
     * {@code 48602 请重新获取验证码}
     */
    BAD_REQ_AGAIN_GET_CODE("48602", "请重新获取验证码"),
    /**
     * {@code 48603 验证码不正确}
     */
    BAD_REQ_CODE_ERROR("48603", "验证码不正确"),
    /**
     * {@code 48604 获取验证码频繁}
     */
    BAD_REQ_GET_CODE_FREQUENTLY("48604", "获取验证码频繁"),
    /**
     * {@code 48605 短信发送异常}
     */
    BAD_REQ_MESSAGE_SEND_ERROR("48605", "短信发送异常"),
    /**
     * {@code 48606 短信发送达到上限}
     */
    BAD_REQ_MESSAGE_SEND_LIMIT("48606", "短信发送达到上限"),
    /**
     * {@code 48608 不合法的推送类型}
     */
    BAD_REQ_INFORMATIONTYPE_ERROR("48608", "不合法的推送类型"),
    /**
     * {@code 48609 不合法的提醒类型}
     */
    BAD_REQ_REMINETYPE_ERROR("48609", "不合法的提醒类型"),
    /**
     * {@code 48102 请勿重复操作}
     */
    BAD_REQ_CONFLICT("48102", "请勿重复操作"),
    /**
     * {@code 58103 退款失败}
     */
    ERR_REFOUND_FAIL("58103", "退款失败"),
    /**
     * {@code 58108 调用支付失败}
     */
    ERR_PAY_RESERVE_FAIL("58108", "调用支付失败"),
    /**
     * {@code 48201 ID必须为空}
     */
    BAD_REQ_ID_MUST_BE_NULL("48201", "ID必须为空"),
    /**
     * {@code 48203 已存在}
     */
    BAD_REQ_EXIST("48203", "已存在"),
    /**
     * {@code 48105 当前人数较多，系统繁忙，请稍后重试}
     */
    BAD_REQ_LIMIT("48105", "当前人数较多，系统繁忙，请稍后重试"),
    /**
     * {@code 40001 参数无效}.
     */
    BAD_PARAMETER("40001", "参数无效"),
    /**
     * 签名验证错误
     */
    INVALID_SIGNATURE("40001", "签名验证错误"),
    /**
     * 48009 系统正在执行导入任务
     */
    PROGRESS("48009", "有任务正在执行中"),

    /**
     * {@code 401 无权限}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7235#section-3.1">HTTP/1.1: Authentication, section 3.1</a>
     */
    UNAUTHORIZED("401", "无权限"),
    /**
     * {@code 403 资源禁止访问}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.3">HTTP/1.1: Semantics and Content, section 6.5.3</a>
     */
    FORBIDDEN("403", "资源禁止访问"),
    /**
     * {@code 404 找不到}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.4">HTTP/1.1: Semantics and Content, section 6.5.4</a>
     */
    NOT_FOUND("404", "找不到"),
    /**
     * {@code 405 方法不允许访问}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.5">HTTP/1.1: Semantics and Content, section 6.5.5</a>
     */
    METHOD_NOT_ALLOWED("405", "方法不允许访问"),
    /**
     * {@code 409 请求冲突}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.8">HTTP/1.1: Semantics and Content, section 6.5.8</a>
     */
    CONFLICT("409", "请求冲突"),
    /**
     * {@code 500 500 服务异常}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.1">HTTP/1.1: Semantics and Content, section 6.6.1</a>
     */
    INTERNAL_SERVER_ERROR("500", "服务异常"),
    /**
     * 操作失败
     */
    FAILED("500", "失败"),
    /**
     * 存在不合法的参数
     */
    VALIDATE_NO_LEGAL("501", "存在不合法的参数"),
    /**
     * 50001 未知错误
     */
    UNKNOWN_ERROR("50001", "未知错误"),
    /**
     * {@code 502 无效网关}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.3">HTTP/1.1: Semantics and Content" ,section 6.6.3</a>
     */
    BAD_GATEWAY("502", "无效网关"),
    /**
     * {@code 503 服务不可用}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.4">HTTP/1.1: Semantics and Content, section 6.6.4</a>
     */
    SERVICE_UNAVAILABLE("503", "服务不可用"),
    /**
     * {@code 504  网关超时}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.5">HTTP/1.1: Semantics and Content, section 6.6.5</a>
     */
    GATEWAY_TIMEOUT("504", "网关超时");

    private final String code;

    private final String msg;

    Status(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * @return 状态码
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return 错误信息
     */
    @Override
    public String getMsg() {
        return msg;
    }

    /**
     * 根据状态码返回符合的SnStatus值，如果没有，返回50001 未知错误
     *
     * @param code 状态码
     * @return 状态
     */
    @Override
    public Status valueOfCode(String code) {
        return StreamEx.of(values()).filter(d -> d.getCode().equals(code)).findFirst().orElse(UNKNOWN_ERROR);
    }


}
