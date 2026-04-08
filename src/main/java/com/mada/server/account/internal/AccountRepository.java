package com.mada.server.account.internal;

import com.mada.server.account.Account;
import com.mada.server.account.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByProviderAndProviderId(OAuthProvider provider, String providerId);
}
