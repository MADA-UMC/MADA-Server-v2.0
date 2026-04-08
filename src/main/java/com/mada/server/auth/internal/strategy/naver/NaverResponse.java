package com.mada.server.auth.internal.strategy.naver;

import lombok.Getter;

@Getter
public class NaverResponse {
    private String resultCode;
    private String message;
    private NaverAccount response;

    @Getter
    public static class NaverAccount {
        private String id;
        private String nickname;
        private String email;
        private String mobile;
    }
}
