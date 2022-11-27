//package com.bondarenko.onlineshop.web.security;
//
//import com.google.common.collect.Sets;
//
//import java.util.Set;
//
//import static com.bondarenko.onlineshop.web.security.ApplicationUserPermission.*;
//
//public enum ApplicationUserRole {
//    USER(Sets.newHashSet()),
//    ADMIN(Sets.newHashSet(ADMIN_ADD, ADMIN_DELETE, ADMIN_UPDATE));
//    private final Set<ApplicationUserPermission> permissions;
//    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
//        this.permissions = permissions;
//    }
//
//    public Set<ApplicationUserPermission> getPermissions() {
//        return permissions;
//    }
//}
