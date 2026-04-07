package com.mada.server.auth.internal;

public class KakaoStrategy implements OAuthStrategy {
    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public OAuthUserInfo loadUserProfile(String accessToken) {
        return null;
    }
}
