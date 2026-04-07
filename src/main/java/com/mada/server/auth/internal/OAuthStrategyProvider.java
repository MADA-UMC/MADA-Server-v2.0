package com.mada.server.auth.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuthStrategyProvider {
    private final List<OAuthStrategy> strategies;

    public OAuthStrategy getStrategy(OAuthProvider provider){
        return this.strategies.stream()
            .filter(s -> s.getProvider() == provider)
            .findFirst()
            .orElseThrow(OAuthProviderNotFoundException::new);
    }
}
