package com.mada.server.auth.internal.dto;

public record AuthTokenDto(
    String accessToken,
    String refreshToken
){}
