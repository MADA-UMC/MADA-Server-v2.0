package com.mada.server.auth.internal;


import jakarta.validation.constraints.NotNull;

public interface OAuthUserInfo {
    @NotNull String getProviderId();
    String getEmail();
    String getPhoneNumber();
}
