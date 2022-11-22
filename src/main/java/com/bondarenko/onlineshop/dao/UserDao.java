package com.bondarenko.onlineshop.dao;

import com.bondarenko.onlineshop.entity.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findUser(String login);
}
