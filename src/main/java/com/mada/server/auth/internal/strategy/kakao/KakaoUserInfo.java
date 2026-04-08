package com.mada.server.auth.internal.strategy.kakao;

import com.mada.server.auth.internal.strategy.OAuthUserInfo;

public class KakaoUserInfo implements OAuthUserInfo {
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
