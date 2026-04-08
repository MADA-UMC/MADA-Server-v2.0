package com.mada.server.auth.internal.strategy.kakao;

import com.mada.server.auth.internal.strategy.OAuthUserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoUserInfo implements OAuthUserInfo {
    private final Long id;
    private final String email;
    private final String phoneNumber;

    @Override
    public String getProviderId() {
        return this.id.toString();
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
