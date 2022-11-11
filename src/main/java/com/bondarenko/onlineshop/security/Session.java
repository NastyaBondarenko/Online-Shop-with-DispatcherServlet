package com.bondarenko.onlineshop.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Session {
    private final String token;
    private final LocalDateTime expireDate;
}
