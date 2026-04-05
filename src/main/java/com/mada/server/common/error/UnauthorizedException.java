package com.mada.server.common.error;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BusinessException {
    private final static HttpStatus statusCode = HttpStatus.UNAUTHORIZED;
    private final static String errorCode = "UNAUTHORIZED";

    public UnauthorizedException(String message) {
        super(message, statusCode, errorCode);
    }
}
