package com.bondarenko.onlineshop.web.security;

import lombok.Getter;

@Getter
public enum ApplicationUserPermission {
    USER_ADD("user:add"),
    USER_DELETE("user:delete"),
    USER_GET("user:get"),
    ADMIN_ADD("admin:add"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_UPDATE("admin:update");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }
}
