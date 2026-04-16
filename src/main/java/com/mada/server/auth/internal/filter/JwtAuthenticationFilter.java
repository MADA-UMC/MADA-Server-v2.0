package com.mada.server.auth.internal.filter;

import com.mada.server.account.Account;
import com.mada.server.account.AccountService;
import com.mada.server.auth.internal.jwt.AccountUserDetails;
import com.mada.server.auth.internal.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final AccountService accountService;

    @Override
    protected void doFilterInternal(
        @Nullable HttpServletRequest request,
        @Nullable HttpServletResponse response,
        @Nullable FilterChain filterChain
    ) throws ServletException, IOException {
        if (request == null || response == null || filterChain == null) {
            return;
        }
        String token = resolveToken(request);

        if (token != null && jwtProvider.isValid(token)) {
            UUID accountId = jwtProvider.extractAccountId(token);
            accountService.findById(accountId).ifPresent(account -> setAuthentication(account, request));
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(Account account, HttpServletRequest request) {
        AccountUserDetails userDetails = new AccountUserDetails(account);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
