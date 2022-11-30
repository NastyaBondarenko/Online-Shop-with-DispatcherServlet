package com.bondarenko.onlineshop.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.bondarenko.onlineshop.security.UserPermission.*;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(ADMIN_ADD, ADMIN_UPDATE, ADMIN_DELETE));

    private final Set<UserPermission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
