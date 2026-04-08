package com.mada.server.auth;

import com.mada.server.account.Account;
import com.mada.server.account.AccountQueryService;
import com.mada.server.auth.internal.AuthService;
import com.mada.server.auth.internal.JwtProvider;
import com.mada.server.auth.internal.strategy.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final AccountQueryService accountQueryService;

    @PostMapping("login")
    public ResponseEntity<Void> login(
        @RequestParam("provider") OAuthProvider provider,
        @RequestHeader("Authorization") String token
    ) {
        OAuthUserInfo oAuthUserInfo = authService.loadOAuthUserProfile(provider,token);
        Account account = accountQueryService.findByProviderAndProviderId(provider, oAuthUserInfo.getProviderId())
            .orElseThrow();
        String accessToken = jwtProvider.createToken(account.getId());
        return ResponseEntity.ok().header("Authorization", accessToken).build();
    }

}
