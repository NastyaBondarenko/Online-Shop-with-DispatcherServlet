package com.bondarenko.onlineshop.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserPermission {
    ADMIN_ADD("admin:add"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_UPDATE("admin:update");

    private final String permission;
}