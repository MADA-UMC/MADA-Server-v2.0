package com.mada.server.auth.internal.strategy.naver;

import lombok.Getter;

@Getter
public class NaverResponse {
    private String resultCode;
    private String message;
    private NaverUserInfo response;
}
