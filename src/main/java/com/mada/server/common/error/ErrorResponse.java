package com.mada.server.common.error;

import java.time.Instant;

public record ErrorResponse(String message, String errorCode, Instant timestamp) {
    public static ErrorResponse by(BusinessException e) {
        return new ErrorResponse(e.getMessage(), e.getErrorCode(), e.getTimestamp());
    }
}
