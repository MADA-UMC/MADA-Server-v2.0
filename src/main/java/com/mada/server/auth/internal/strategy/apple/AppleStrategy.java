package com.mada.server.auth.internal.strategy.apple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mada.server.account.OAuthProvider;
import com.mada.server.auth.internal.strategy.OAuthStrategy;
import com.mada.server.auth.internal.strategy.OAuthUserInfo;
import com.mada.server.common.error.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@Component
public class AppleStrategy implements OAuthStrategy {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public AppleStrategy() {
        this.webClient = WebClient.builder().build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.APPLE;
    }

    @Override
    public AppleUserInfo loadUserProfile(String identityToken) {
        String kid = extractKid(identityToken);

        ApplePublicKeyResponse keyResponse = webClient.get()
            .uri("https://appleid.apple.com/auth/keys")
            .retrieve()
            .bodyToMono(ApplePublicKeyResponse.class)
            .block();

        if (keyResponse == null) {
            throw new UnauthorizedException("Apple 공개키를 가져올 수 없습니다.");
        }

        ApplePublicKeyResponse.ApplePublicKey publicKey = keyResponse.getKeys().stream()
            .filter(k -> k.getKid().equals(kid))
            .findFirst()
            .orElseThrow(() -> new UnauthorizedException("Apple 공개키를 찾을 수 없습니다."));

        PublicKey rsaKey = buildPublicKey(publicKey);

        Claims claims = Jwts.parser()
            .verifyWith(rsaKey)
            .build()
            .parseSignedClaims(identityToken)
            .getPayload();

        return new AppleUserInfo(claims.getSubject(), claims.get("email", String.class));
    }

    private String extractKid(String token) {
        try {
            String header = token.split("\\.")[0];
            String decoded = new String(Base64.getUrlDecoder().decode(header));
            return objectMapper.readTree(decoded).get("kid").asText();
        } catch (Exception e) {
            throw new UnauthorizedException("Apple 토큰 파싱에 실패했습니다.");
        }
    }

    private PublicKey buildPublicKey(ApplePublicKeyResponse.ApplePublicKey key) {
        try {
            byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
            byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());
            RSAPublicKeySpec spec = new RSAPublicKeySpec(
                new BigInteger(1, nBytes),
                new BigInteger(1, eBytes)
            );
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (Exception e) {
            throw new UnauthorizedException("Apple 공개키 생성에 실패했습니다.");
        }
    }
}
