package com.mada.server.auth.internal.strategy;


import jakarta.validation.constraints.NotNull;

public interface OAuthUserInfo {
    @NotNull String getProviderId();
    String getEmail();
    String getPhoneNumber();
}
