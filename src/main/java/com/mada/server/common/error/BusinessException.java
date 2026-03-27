package com.mada.server.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
public abstract class BusinessException extends RuntimeException {
    protected final String message;
    protected final HttpStatus statusCode;
    protected final String errorCode;
    protected final Instant timestamp;

    public BusinessException(String message, HttpStatus statusCode, String errorCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
        super(message);
    }
}
