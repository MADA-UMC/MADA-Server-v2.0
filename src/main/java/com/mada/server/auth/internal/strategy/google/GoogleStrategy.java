package com.mada.server.auth.internal.strategy.google;

import com.mada.server.auth.OAuthProvider;
import com.mada.server.auth.internal.strategy.OAuthStrategy;
import com.mada.server.auth.internal.strategy.OAuthUserInfo;
import org.springframework.stereotype.Component;

@Component
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
