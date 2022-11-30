package com.bondarenko.onlineshop.dao.jdbc.repository.impl;

import com.bondarenko.onlineshop.dao.jdbc.repository.UserRepository;
import com.bondarenko.onlineshop.entity.User;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.bondarenko.onlineshop.security.UserRole.ADMIN;
import static com.bondarenko.onlineshop.security.UserRole.USER;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findUser(String userName) {
        return getUser()
                .stream()
                .filter(applicationUser -> userName.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<User> getUser() {
        return Lists.newArrayList(
                new User(
                        "user",
                        passwordEncoder.encode("pass"),
                        USER.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new User("admin",
                        passwordEncoder.encode("admin"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true)
        );
    }
}