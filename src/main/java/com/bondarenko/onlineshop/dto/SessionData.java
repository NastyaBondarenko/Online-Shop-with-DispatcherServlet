package com.bondarenko.onlineshop.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class SessionData {
    private final String token;
    private final int sessionTime;
}
