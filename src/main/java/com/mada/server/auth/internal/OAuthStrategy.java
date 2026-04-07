package com.mada.server.auth.internal;

public interface OAuthStrategy {
    OAuthProvider getProvider();

    OAuthUserInfo loadUserProfile(String accessToken);
}
