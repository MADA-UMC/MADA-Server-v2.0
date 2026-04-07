package com.mada.server.account;

import com.mada.server.account.internal.AccountRepository;
import com.mada.server.auth.internal.OAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountQueryService {

    private final AccountRepository accountRepository;

    public Optional<Account> findById(UUID id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findByProviderAndProviderId(OAuthProvider provider, String providerId) {
        return this.accountRepository.findByProviderAndProviderId(provider,providerId)
    }
}
