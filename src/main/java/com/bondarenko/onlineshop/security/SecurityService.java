package com.bondarenko.onlineshop.security;

import com.bondarenko.onlineshop.entity.TokenAndSessionLifeTime;
import com.bondarenko.onlineshop.entity.User;
import com.bondarenko.onlineshop.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SecurityService {
    private final List<Session> sessionList = new ArrayList<>();
    private final UserService userService;
    private final int sessionLifeTime;

    @Autowired
    public SecurityService(UserService userService,@Value("${session.sessionLifeTime}") String sessionLifeTime) {
        this.userService = userService;
        this.sessionLifeTime = Integer.parseInt(sessionLifeTime);
    }

    public String encryptPasswordWithSalt(String password,  String login) {
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

    public TokenAndSessionLifeTime login(String login, String password) {
        if (isValidCredential(login, password)) {
            String token = generateCookie().getValue();
            LocalDateTime expireDataTime = LocalDateTime.now().plusSeconds(sessionLifeTime);
            Session session = new Session(token, expireDataTime);
            sessionList.add(session);
            TokenAndSessionLifeTime tokenAndSessionLifeTime = new TokenAndSessionLifeTime(token, sessionLifeTime);

            return tokenAndSessionLifeTime;
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
//        userTokens.add(userToken);
        return new Cookie("user-token", userToken);
    }

    public boolean isAuth(Cookie[] cookies) {
        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("user-token")) {
//                    if (userTokens.contains(cookie.getValue())) {
            return true;
//                    }
//                }
//            }
//        }


        }

        return false;
    }
}


