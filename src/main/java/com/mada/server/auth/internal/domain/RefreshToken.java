package com.mada.server.auth.internal.domain;

import com.mada.server.account.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refresh_token", indexes = {
    @Index(name = "idx_refresh_token_account_id", columnList = "account_id"),
    @Index(name = "idx_refresh_token_token", columnList = "token", unique = true)
})
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "expire_at")
    private Instant expireAt;
}
