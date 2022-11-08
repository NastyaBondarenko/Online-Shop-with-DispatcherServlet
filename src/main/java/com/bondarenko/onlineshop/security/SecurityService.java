package com.bondarenko.onlineshop.security;

import com.bondarenko.onlineshop.entity.User;
import com.bondarenko.onlineshop.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class SecurityService {
    private final UserService userService;
    private final List<String> userTokens = new ArrayList<>();

    public SecurityService(UserService userService) {
        this.userService = userService;
    }

    public String encryptPasswordWithSalt(String password, String login) {
        String salt = getSalt(login);
        String passwordWithSalt = password + salt;

        return hash(passwordWithSalt);
    }

    public boolean isValidCredential(String login, String password) {
        User userFromDB = userService.findUser(login);
        if (userFromDB != null) {
            String encryptedPassword = encryptPasswordWithSalt(password, login);
            String passwordFromDB = userFromDB.getPassword();
            return Objects.equals(encryptedPassword, passwordFromDB);
        }
        return false;
    }

    public String login(String login, String password) {
        if (isValidCredential(login, password)) {
            String token = generateCookie().getValue();
            return token;
        }
        return null;
    }

    public String hash(String value) {
        return DigestUtils.md5Hex(value);
    }

    public String getSalt(String login) {
        User userLogin = userService.findUser(login);
        return userLogin.getSalt();
    }

    public String generateSalt() {
        return UUID.randomUUID().toString();
    }
    //

    //__
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


