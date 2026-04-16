package com.mada.server.auth.internal.strategy.google;

import com.mada.server.auth.internal.strategy.OAuthUserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GoogleUserInfo implements OAuthUserInfo {
    private final String id;
    private final String email;

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
        return null;
    }
}
