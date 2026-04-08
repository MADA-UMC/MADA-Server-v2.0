package com.mada.server.auth.internal.strategy;

import com.mada.server.auth.OAuthProvider;

public interface OAuthStrategy {
    OAuthProvider getProvider();

    OAuthUserInfo loadUserProfile(String accessToken);
}
