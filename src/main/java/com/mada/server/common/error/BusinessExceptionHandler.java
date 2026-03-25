package com.mada.server.common.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class BusinessExceptionHandler {
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException e) {
        var status = e.getStatusCode();
        var errorResponse = ErrorResponse.by(e);
        return ResponseEntity.status(status).body(errorResponse);
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var errorResponse = new ErrorResponse("Internal Server Error", null, Instant.now());
        return ResponseEntity.status(status).body(errorResponse);
    }
}
