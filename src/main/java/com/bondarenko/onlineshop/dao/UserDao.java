package com.bondarenko.onlineshop.dao;

import com.bondarenko.onlineshop.entity.User;

public interface UserDao {
    User findUser(String login);
}
