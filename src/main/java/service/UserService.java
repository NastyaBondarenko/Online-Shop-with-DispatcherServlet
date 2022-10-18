package service;

import dao.UserDao;
import entity.User;
import jakarta.servlet.http.Cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserDao userDao;
    private final List<String> userTokens = new ArrayList<>();

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

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
