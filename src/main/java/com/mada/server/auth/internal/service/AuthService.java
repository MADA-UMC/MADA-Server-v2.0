package com.mada.server.auth.internal.service;

import com.mada.server.account.Account;
import com.mada.server.account.AccountService;
import com.mada.server.account.OAuthProvider;
import com.mada.server.auth.internal.jwt.JwtProvider;
import com.mada.server.auth.internal.domain.RefreshToken;
import com.mada.server.auth.internal.repository.RefreshTokenRepository;
import com.mada.server.util.RandomStringGenerator;
import com.mada.server.auth.internal.dto.AuthTokenDto;
import com.mada.server.auth.internal.strategy.OAuthStrategyProvider;
import com.mada.server.auth.internal.strategy.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final OAuthStrategyProvider strategyProvider;
    private final AccountService accountService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public OAuthUserInfo loadOAuthUserProfile(OAuthProvider provider, String token) {
        return strategyProvider.getStrategy(provider).loadUserProfile(token);
    }

    @Transactional
    public AuthTokenDto login(OAuthProvider provider, String token) {
        OAuthUserInfo oAuthUserInfo = loadOAuthUserProfile(provider, token);
        Account account = accountService.getOrCreateAccount(provider, oAuthUserInfo.getProviderId());

        String accessToken = jwtProvider.createToken(account.getId());
        String randomStr = RandomStringGenerator.generate();

        Instant refreshTokenExpireAt = Instant.now().plus(7, DAYS);

        RefreshToken refreshToken = RefreshToken.builder()
            .account(account)
            .token(randomStr)
            .expireAt(refreshTokenExpireAt)
            .build();

        refreshTokenRepository.save(refreshToken);

        return new AuthTokenDto(accessToken, randomStr);
    }

}
