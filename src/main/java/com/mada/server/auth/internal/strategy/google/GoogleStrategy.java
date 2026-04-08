package com.mada.server.auth.internal.strategy.google;

import com.mada.server.account.OAuthProvider;
import com.mada.server.auth.internal.strategy.OAuthStrategy;
import com.mada.server.auth.internal.strategy.OAuthUserInfo;
import com.mada.server.common.error.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GoogleStrategy implements OAuthStrategy {
    private final WebClient webClient;

    public GoogleStrategy() {
        this.webClient = WebClient.builder().build();
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.GOOGLE;
    }

    @Override
    public GoogleUserInfo loadUserProfile(String accessToken) {

        GoogleResponse response = webClient.get()
            .uri("https://www.googleapis.com/oauth2/v3/userinfo")
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(GoogleResponse.class)
            .block();

        if (response == null) {
            throw new UnauthorizedException("Google 인증에 실패했습니다.");
        }

        return new GoogleUserInfo(response.getSub(), response.getEmail());
    }
}
