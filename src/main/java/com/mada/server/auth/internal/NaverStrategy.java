package com.mada.server.auth.internal;

public class NaverStrategy implements OAuthStrategy {
    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public OAuthUserInfo loadUserProfile(String accessToken) {
        return null;
    }
}
