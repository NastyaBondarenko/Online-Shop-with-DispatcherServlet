package com.bondarenko.onlineshop.security;

import com.bondarenko.onlineshop.entity.SessionData;
import com.bondarenko.onlineshop.entity.User;
import com.bondarenko.onlineshop.service.UserService;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SecurityService {
    private final CopyOnWriteArrayList<Session> sessionList = new CopyOnWriteArrayList<>();
    private final PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
    private final UserService userService;
    private final int sessionTime;

    public SecurityService(UserService userService, @Value("${session.sessionLifeTime}") String sessionTime) {
        this.userService = userService;
        this.sessionTime = Integer.parseInt(sessionTime);
    }

    public SessionData login(String login, String password) {
        if (isValidCredential(login, password)) {
            String token = passwordEncryptor.generateToken();
            LocalDateTime expireDataTime = LocalDateTime.now().plusSeconds(sessionTime);
            Session session = new Session(token, expireDataTime);
            sessionList.add(session);

            return new SessionData(token, sessionTime);
        }
        return null;
    }

    public boolean isAuth(Cookie[] cookies) {
        String userToken;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                userToken = cookie.getValue();
                for (Session session : sessionList) {
                    if (session.getToken().equals(userToken)) {
                        if (session.getExpireDate().isAfter(LocalDateTime.now())) {
                            return true;
                        }
                    }
                    sessionList.remove(session);
                    break;
                }
            }
        }
        return false;
    }

    @VisibleForTesting
    boolean isValidCredential(String login, String password) {
        User userFromDB = userService.findUser(login);
        if (userFromDB != null) {
            String salt = getSalt(login);
            String encryptedPassword = passwordEncryptor.encryptPasswordWithSalt(password, salt);
            String passwordFromDB = userFromDB.getPassword();
            return Objects.equals(encryptedPassword, passwordFromDB);
        }
        return false;
    }

    @VisibleForTesting
    String getSalt(String login) {
        User userLogin = userService.findUser(login);
        return userLogin.getSalt();
    }
}


