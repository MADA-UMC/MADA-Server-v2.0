package com.mada.server.account;

import java.util.UUID;

public record AccountDto(
    UUID id,
    String nickname
){}
