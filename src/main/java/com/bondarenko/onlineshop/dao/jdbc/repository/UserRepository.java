package com.bondarenko.onlineshop.dao.jdbc.repository;

import com.bondarenko.onlineshop.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUser(String userName);
}