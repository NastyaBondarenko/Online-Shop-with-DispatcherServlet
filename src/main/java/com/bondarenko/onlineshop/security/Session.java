package com.bondarenko.onlineshop.security;

import com.bondarenko.onlineshop.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Session {
    private final String token;
    private final LocalDateTime expireDate;
    private final User user;
}
