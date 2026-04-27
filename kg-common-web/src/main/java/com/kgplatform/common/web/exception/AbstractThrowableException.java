package com.kgplatform.common.web.exception;



import com.kgplatform.common.web.core.IEnum;
import com.kgplatform.common.web.core.Status;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * @author : Wuyibo
 * @since : 2020/7/21 17:00
 */
public abstract class AbstractThrowableException extends RuntimeException implements Problem {

    private final Status defaultStatus = Status.BAD_REQUEST;

    private final String defaultCode = defaultStatus.getCode();

    private final String defaultMessage = defaultStatus.getMsg();

    private final String code;

    private final String message;

    protected AbstractThrowableException(IEnum<String> status) {
        this(status, status.getMsg());
    }

    protected AbstractThrowableException(IEnum<String> status, String message) {
        this(ObjectUtils.defaultIfNull(status, Status.BAD_REQUEST).getCode(), message);
    }

    protected AbstractThrowableException(String message) {
        this.code = defaultCode;
        this.message = StringUtils.defaultIfBlank(message, defaultMessage);
    }

    protected AbstractThrowableException(String code, String message) {
        this.code = StringUtils.defaultIfBlank(code, defaultCode);
        this.message = StringUtils.defaultIfBlank(message, defaultMessage);
    }

    protected AbstractThrowableException(Throwable cause) {
        this(null, cause);
    }

    protected AbstractThrowableException(String message, Throwable cause) {
        super(cause);
        this.code = defaultCode;
        this.message = StringUtils.defaultIfBlank(message, defaultMessage);
    }

    @Override
    public String getCode() {
        return StringUtils.defaultIfBlank(this.code, defaultCode);
    }

    @Override
    public String getMessage() {
        return StringUtils.defaultIfBlank(this.message, defaultMessage);
    }

    @Override
    public String toString() {
        return Problem.toString(this);
    }
}
