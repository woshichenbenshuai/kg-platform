package com.kgplatform.common.web.exception;



import com.kgplatform.common.web.core.IEnum;
import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.core.Status;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 自定义API异常
 *
 * @author : Wuyibo
 * @since : 2020/7/21 17:02
 */
public class ApiException extends AbstractThrowableException {

    private static final long serialVersionUID = -1442406338299946127L;

    private Status errorCode;

    private Result<Object> commonResult;

    /**
     * 兼容cause为ApiException时存储状态码
     */
    private String causeCode;
    private String causeMsg;

    public ApiException(IEnum<String> status) {
        super(status);
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Result<Object> commonResult) {
        super(commonResult.getCode(), commonResult.getMsg());
        this.commonResult = commonResult;
    }

    public ApiException(Status errorCode) {
        super(errorCode.getCode(), errorCode.getMsg());
        this.errorCode = errorCode;
    }

    /**
     * 通过异常实例化 api exception
     *
     * @param cause 异常
     */
    public ApiException(Throwable cause) {
        super(cause);
        if (!(cause instanceof ApiException)) {
            return;
        }
        ApiException apiException = (ApiException) cause;
        if (Objects.nonNull(apiException.errorCode)) {
            this.errorCode = apiException.errorCode;
        }
        if (Objects.nonNull(apiException.commonResult)) {
            this.commonResult = apiException.commonResult;
        }

        this.causeCode = apiException.getCode();
        this.causeMsg = apiException.getMessage();
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public Status getErrorCode() {
        return errorCode;
    }

    public Result<Object> getCommonResult() {
        return commonResult;
    }

    @Override
    public String getCode() {
        return StringUtils.defaultIfBlank(this.causeCode, super.getCode());
    }

    @Override
    public String getMessage() {
        return StringUtils.defaultIfBlank(this.causeMsg, super.getMessage());
    }
}
