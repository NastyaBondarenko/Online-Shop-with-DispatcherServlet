package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.dao.UserDao;
import com.bondarenko.onlineshop.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public User findUser(String login) {

        return userDao.findUser(login);
    }


}
