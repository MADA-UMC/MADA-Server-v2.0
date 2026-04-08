package com.mada.server.auth.internal.strategy.naver;

import com.mada.server.auth.internal.strategy.OAuthUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class NaverUserInfo implements OAuthUserInfo {
    private final String id;
    private final String email;
    private final String phoneNumber;

    @Override
    public String getProviderId() {
        return this.id;
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
