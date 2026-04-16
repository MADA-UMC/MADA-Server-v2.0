package com.mada.server.auth.internal.strategy.naver;

import com.mada.server.account.OAuthProvider;
import com.mada.server.auth.internal.strategy.OAuthStrategy;
import com.mada.server.common.error.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NaverStrategy implements OAuthStrategy {
    private final WebClient webClient;

    public NaverStrategy() {
        this.webClient = WebClient.builder().build();
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public NaverUserInfo loadUserProfile(String accessToken) {
        NaverResponse response = webClient.get()
            .uri("https://openapi.naver.com/v1/nid/me")
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(NaverResponse.class)
            .block();

        if (response == null) {
            throw new UnauthorizedException("Naver 인증에 실패했습니다.");
        }

        var naverAcount =  response.getResponse();
        return new NaverUserInfo(naverAcount.getId(), naverAcount.getEmail(), naverAcount.getMobile());
    }
}
