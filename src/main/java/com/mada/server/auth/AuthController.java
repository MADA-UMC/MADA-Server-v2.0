package com.mada.server.auth;

import com.mada.server.account.OAuthProvider;
import com.mada.server.auth.internal.service.AuthService;
import com.mada.server.auth.internal.dto.AuthTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthTokenDto> login(
        @RequestParam("provider") OAuthProvider provider,
        @RequestHeader("Authorization") String token
    ) {
        AuthTokenDto authTokenDto = authService.login(provider, token);
        return ResponseEntity.ok().body(authTokenDto);
    }

}
