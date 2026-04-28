package com.kgplatform.common.web.exception;

import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.core.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Result<Object>> handleApiException(ApiException ex) {
        return ResponseEntity.status(resolveHttpStatus(ex.getCode())).body(buildResult(ex));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Object>> handleException(Exception ex) {
        Result<Object> result = Result.failed(Status.INTERNAL_SERVER_ERROR).internalMsg(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    private Result<Object> buildResult(ApiException ex) {
        if (ex.getCommonResult() != null) {
            return ex.getCommonResult();
        }
        return new Result<>(null, ex.getMessage(), ex.getCode());
    }

    private HttpStatus resolveHttpStatus(String code) {
        if (Status.UNAUTHORIZED.getCode().equals(code)) {
            return HttpStatus.UNAUTHORIZED;
        }
        if (Status.FORBIDDEN.getCode().equals(code)) {
            return HttpStatus.FORBIDDEN;
        }
        if (Status.NOT_FOUND.getCode().equals(code)) {
            return HttpStatus.NOT_FOUND;
        }
        if (Status.METHOD_NOT_ALLOWED.getCode().equals(code)) {
            return HttpStatus.METHOD_NOT_ALLOWED;
        }
        if (Status.CONFLICT.getCode().equals(code)) {
            return HttpStatus.CONFLICT;
        }
        if (Status.BAD_GATEWAY.getCode().equals(code)) {
            return HttpStatus.BAD_GATEWAY;
        }
        if (Status.SERVICE_UNAVAILABLE.getCode().equals(code)) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
        if (Status.GATEWAY_TIMEOUT.getCode().equals(code)) {
            return HttpStatus.GATEWAY_TIMEOUT;
        }
        if (code != null && code.startsWith("4")) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
