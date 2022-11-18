package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.dao.UserDao;
import com.bondarenko.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public Optional<User> findUser(String login) {
        return Optional.ofNullable(userDao.findUser(login));
    }
}
