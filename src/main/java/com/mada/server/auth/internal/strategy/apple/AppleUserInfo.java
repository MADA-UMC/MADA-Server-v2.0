package com.mada.server.auth.internal.strategy.apple;

import com.mada.server.auth.internal.strategy.OAuthUserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AppleUserInfo implements OAuthUserInfo {
    private final String sub;
    private final String email;

    @Override
    public String getProviderId() {
        return this.sub;
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
