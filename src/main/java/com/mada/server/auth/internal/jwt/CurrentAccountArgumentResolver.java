package com.mada.server.auth.internal.jwt;

import com.mada.server.account.Account;
import com.mada.server.account.AccountDto;
import com.mada.server.auth.CurrentAccount;
import com.mada.server.common.error.UnauthorizedException;
import jakarta.annotation.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentAccountArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentAccount.class)
            && parameter.getParameterType().equals(Account.class);
    }

    @Override
    public AccountDto resolveArgument(
        @Nullable MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        @Nullable NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AccountUserDetails userDetails)) {
            throw new UnauthorizedException("인증된 사용자가 없습니다.");
        }
        return userDetails.getAccount();
    }
}
