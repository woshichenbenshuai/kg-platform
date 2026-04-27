package com.kgplatform.common.web.core;

import org.apache.commons.text.RandomStringGenerator;

import java.time.format.DateTimeFormatter;

/**
 * AppConstant
 *
 * @author chen
 * @since 2026-04-23 17:09:48
 */
public final class AppConstant {
    private AppConstant() {
    }

    /**
     * 顺众ip
     */
    public static final String CLIENT_IP_KEY = "shunzhong.client.ip";
    /**
     * 开发环境
     */
    public static final String DEV_CODE = "dev";
    /**
     * 生产环境
     */
    public static final String PROD_CODE = "prod";
    /**
     * 测试环境
     */
    public static final String TEST_CODE = "test";

    /**
     * 默认租户ID
     */
    public static final String DEFAULT_TENANT_ID = "000000";
    /**
     * apache commons text 数字生成器
     */
    public static final RandomStringGenerator NUMERIC_GENERATOR =
            new RandomStringGenerator.Builder().withinRange('0', '9').build();

    /* date format 相关的 开始 */

    /**
     * 日期格式化通用格式，年年年年-月月-日日 例如 2020-03-01
     */
    public static final String FORMAT_PATTERN_DATE = "yyyy-MM-dd";
    /**
     * 日期格式化通用格式，时时:分分:秒秒 例如 12:30:30
     */
    public static final String FORMAT_PATTERN_TIME = "HH:mm:ss";
    /**
     * 时间格式化通用格式，年年年年-月月-日日 时时:分分:秒秒 例如 2020-03-01 00:00:00
     */
    public static final String FORMAT_PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式化通用格式，年年年年月月日日时时分分秒秒 例如 20200301000000
     */
    public static final String FORMAT_PATTERN_DATE_TIME_COMPACT = "yyyyMMddHHmmss";
    /**
     * 时间格式化通用格式，年年年年月月日日时时分分秒秒 例如 20200301000000
     *
     * @see AppConstant#FORMAT_PATTERN_DATE_TIME_COMPACT
     */
    public static final DateTimeFormatter FORMATER_DATE_TIME_COMPACT =
            DateTimeFormatter.ofPattern(FORMAT_PATTERN_DATE_TIME_COMPACT);
    /**
     * 时间格式化通用格式，年年年年-月月-日日 时时:分分:秒秒 例如 2020-03-01 00:00:00
     *
     * @see AppConstant#FORMAT_PATTERN_DATE_TIME
     */
    public static final DateTimeFormatter FORMATER_DATE_TIME = DateTimeFormatter.ofPattern(FORMAT_PATTERN_DATE_TIME);
    /**
     * 日期格式化通用格式，时时:分分:秒秒 例如 12:30:30
     *
     * @see AppConstant#FORMAT_PATTERN_TIME
     */
    public static final DateTimeFormatter FORMATER_TIME = DateTimeFormatter.ofPattern(FORMAT_PATTERN_TIME);
    /**
     * 日期格式化通用格式，年年年年-月月-日日 例如 2020-03-01
     *
     * @see AppConstant#FORMAT_PATTERN_DATE
     */
    public static final DateTimeFormatter FORMATER_DATE = DateTimeFormatter.ofPattern(FORMAT_PATTERN_DATE);

    /* date format 相关的 结束 */

    /**
     * 管理员角色
     */
    public static final String AUTH_ADMIN = "ROLE_ADMIN";

    /**
     * 用户角色
     */
    public static final String AUTH_USER = "ROLE_USER";

    /**
     * 匿名者
     */
    public static final String AUTH_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * 系统账号
     */
    public static final String SYSTEM_ACCOUNT = "system";

    /**
     * 匿名账号
     */
    public static final String ANONYMOUS_ACCOUNT = "anonymousUser";

    /**
     * 内部账号
     */
    public static final String INTERNAL_ACCOUNT = "internal";

    /**
     * 认证头 key {@code Authorization }
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";
    /**
     * Bearer token前缀 {@code Bearer}
     */
    public static final String BEARER_TOKEN_TYPE = "Bearer";

    /**
     * 批量日志key
     */
    public static final String LOG_BATCH_KEY = "batch_no";

    /**
     * 表情字符串的Unicode编码
     */
    public static final String EMOJI_UNICODE = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\p{Sc}\\s]";

    /**
     * 日志通过redis中转的key，中转后通过mq发出
     */
    public static final String LOG_TRANSFER_REDIS_KEY = "log:";

    /**
     * 时间24小时
     */
    public static final Integer TIME_MS_24 = 24 * 60 * 60 * 1000;
}
