package com.mada.server.auth.internal.strategy.apple;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ApplePublicKeyResponse {
    private List<ApplePublicKey> keys;

    @Getter
    @NoArgsConstructor
    public static class ApplePublicKey {
        private String kty;
        private String kid;
        private String use;
        private String alg;
        private String n;
        private String e;
    }
}
