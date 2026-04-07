package com.mada.server.account;

import com.mada.server.auth.internal.OAuthProvider;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account", uniqueConstraints = {
    @UniqueConstraint(
        name = "uk_provider_provider_id",
        columnNames = {"provider, provider_id"}
    ),
})
public class Account {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(nullable = false)
    private String nickname;
}
