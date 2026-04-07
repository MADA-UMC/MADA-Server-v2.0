package com.mada.server.auth.internal;

public class GoogleStrategy implements OAuthStrategy {
    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.GOOGLE;
    }

    @Override
    public OAuthUserInfo loadUserProfile(String accessToken) {
        return null;
    }
}
