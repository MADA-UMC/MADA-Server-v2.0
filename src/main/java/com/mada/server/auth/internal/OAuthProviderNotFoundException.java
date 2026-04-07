package com.mada.server.auth.internal;

import com.mada.server.common.error.NotFoundException;

public class OAuthProviderNotFoundException extends NotFoundException {
    private static final String DEFAULT_MESSAGE = "지원하지 않는 OAuth 제공자입니다.";

    public OAuthProviderNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public OAuthProviderNotFoundException(String message) {
        super(message);
    }
}
