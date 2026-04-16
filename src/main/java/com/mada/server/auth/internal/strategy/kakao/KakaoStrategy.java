package com.mada.server.auth.internal.strategy.kakao;

import com.mada.server.account.OAuthProvider;
import com.mada.server.auth.internal.strategy.OAuthStrategy;
import com.mada.server.auth.internal.strategy.OAuthUserInfo;
import com.mada.server.common.error.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoStrategy implements OAuthStrategy {
    private final WebClient webClient;

    public KakaoStrategy() {
        this.webClient = WebClient.builder().build();
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public KakaoUserInfo loadUserProfile(String accessToken) {
        KakaoResponse response = webClient.get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(KakaoResponse.class)
            .block();

        if (response == null) {
            throw new UnauthorizedException("Kakao 인증에 실패했습니다.");
        }

        var account = response.getKakaoAccount();
        if (account == null) {
            throw new UnauthorizedException("Kakao 계정 정보를 가져올 수 없습니다.");
        }

        return new KakaoUserInfo(
            response.getId(),
            account.getEmail(),
            account.getPhoneNumber()
        );
    }
}
