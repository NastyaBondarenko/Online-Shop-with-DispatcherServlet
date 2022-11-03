package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.dao.UserDao;
import com.bondarenko.onlineshop.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
public class UserService {
    private UserDao userDao;
    private final List<String> userTokens = new ArrayList<>();

    public User findUser(String login) {

        return userDao.findUser(login);
    }

    public Cookie generateCookie() {
        String userToken = UUID.randomUUID().toString();
        userTokens.add(userToken);
        return new Cookie("user-token", userToken);
    }

    public boolean isAuth(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (userTokens.contains(cookie.getValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
