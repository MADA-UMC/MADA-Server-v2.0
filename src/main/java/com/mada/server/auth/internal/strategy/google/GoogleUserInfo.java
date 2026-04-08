package com.mada.server.auth.internal.strategy.google;

import com.mada.server.auth.internal.strategy.OAuthUserInfo;

public class GoogleUserInfo implements OAuthUserInfo {
    @Override
    public String getProviderId() {
        return "";
    }

    @Override
    public String getEmail() {
        return "";
    }

    @Override
    public String getPhoneNumber() {
        return "";
    }
}
