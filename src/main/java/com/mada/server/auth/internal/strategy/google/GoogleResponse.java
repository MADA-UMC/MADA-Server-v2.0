package com.mada.server.auth.internal.strategy.google;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleResponse {
    private String sub;
    private String email;
}
