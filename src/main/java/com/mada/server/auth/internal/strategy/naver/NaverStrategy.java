package com.mada.server.auth.internal.strategy.naver;

import com.mada.server.auth.OAuthProvider;
import com.mada.server.auth.internal.strategy.OAuthStrategy;
import com.mada.server.common.error.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NaverStrategy implements OAuthStrategy {
    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public NaverUserInfo loadUserProfile(String accessToken) {
        WebClient webClient = WebClient.builder().build();
        String authEndpoint = "openapi.naver.com/v1/nid/me";

        NaverResponse response = webClient.get()
            .uri(authEndpoint)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(NaverResponse.class)
            .block();

        if (response == null) {
            throw new UnauthorizedException("Naver 인증에 실패했습니다.");
        }
        return response.getResponse();
    }
}
