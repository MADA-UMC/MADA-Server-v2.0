package com.mada.server.auth.internal.strategy.kakao;

import com.mada.server.auth.OAuthProvider;
import com.mada.server.auth.internal.strategy.OAuthStrategy;
import com.mada.server.auth.internal.strategy.OAuthUserInfo;
import org.springframework.stereotype.Component;

@Component
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
