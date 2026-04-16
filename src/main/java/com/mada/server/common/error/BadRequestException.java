package com.mada.server.common.error;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BusinessException {
    private final static HttpStatus statusCode = HttpStatus.BAD_REQUEST;
    private final static String errorCode = "BAD_REQUEST";

    public BadRequestException(String message) {
        super(message, statusCode, errorCode);
    }
}
