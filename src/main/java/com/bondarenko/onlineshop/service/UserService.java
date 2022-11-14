package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.dao.UserDao;
import com.bondarenko.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public User findUser(String login) {
        return userDao.findUser(login);
    }
}
