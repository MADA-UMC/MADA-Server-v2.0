package com.mada.server.common.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException{

    private final static HttpStatus statusCode = HttpStatus.NOT_FOUND;
    private final static String errorCode = "NOT_FOUND";

    public NotFoundException(String message) {
        super(message, statusCode, errorCode);
    }
}
