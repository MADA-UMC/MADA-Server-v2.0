package com.mada.server.auth.internal;

import com.mada.server.auth.OAuthProvider;
import com.mada.server.auth.internal.strategy.OAuthStrategyProvider;
import com.mada.server.auth.internal.strategy.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final OAuthStrategyProvider strategyProvider;

    public OAuthUserInfo loadOAuthUserProfile(OAuthProvider provider, String token) {
        return strategyProvider.getStrategy(provider).loadUserProfile(token);
    }

}
