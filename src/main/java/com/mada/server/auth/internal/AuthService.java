package com.mada.server.auth.internal;

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
