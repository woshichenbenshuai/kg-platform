package com.kgplatform.common.web.exception;

import com.kgplatform.common.web.core.Result;
import com.kgplatform.common.web.core.Status;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleApiException_should_return_401_json_for_unauthorized() {
        ResponseEntity<Result<Object>> response = globalExceptionHandler.handleApiException(new ApiException(Status.UNAUTHORIZED));

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("401", response.getBody().getCode());
        assertEquals(Status.UNAUTHORIZED.getMsg(), response.getBody().getMsg());
    }
}
