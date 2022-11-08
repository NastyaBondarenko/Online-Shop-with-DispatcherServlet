package com.bondarenko.onlineshop.security;

import com.bondarenko.onlineshop.entity.User;
import com.bondarenko.onlineshop.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class SecurityService {
    private final UserService userService;

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
            String token = userService.generateCookie().getValue();
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
}


