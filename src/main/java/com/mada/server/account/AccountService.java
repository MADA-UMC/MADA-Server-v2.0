package com.mada.server.account;

import com.mada.server.account.internal.AccountRepository;
import com.mada.server.util.NicknameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public Optional<Account> findById(UUID id) {
        return accountRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Account> findByProviderAndProviderId(OAuthProvider provider, String providerId) {
        return this.accountRepository.findByProviderAndProviderId(provider, providerId);
    }

    @Transactional
    public Account getOrCreateAccount(OAuthProvider provider, String providerId) {
        Optional<Account> account = findByProviderAndProviderId(provider, providerId);

        return account.orElseGet(() -> createAccount(provider, providerId));
    }

    @Transactional
    public Account createAccount(OAuthProvider provider, String providerId) {
        Account account = Account.builder()
            .provider(provider)
            .providerId(providerId)
            .nickname(NicknameGenerator.generate())
            .build();
        return accountRepository.save(account);
    }
}
