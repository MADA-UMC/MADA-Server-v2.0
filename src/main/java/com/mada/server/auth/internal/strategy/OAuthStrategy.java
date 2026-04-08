package com.mada.server.auth.internal.strategy;

import com.mada.server.account.OAuthProvider;

public interface OAuthStrategy {
    OAuthProvider getProvider();

    OAuthUserInfo loadUserProfile(String accessToken);
}
