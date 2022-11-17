package com.bondarenko.onlineshop.security;

import com.bondarenko.onlineshop.dto.SessionData;
import com.bondarenko.onlineshop.entity.User;
import com.bondarenko.onlineshop.service.UserService;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
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

    public Optional<SessionData> login(String login, String password) {
        if (isValidCredential(login, password)) {
            String token = passwordEncryptor.generateToken();
            LocalDateTime expireDataTime = LocalDateTime.now().plusSeconds(sessionTime);
            Session session = new Session(token, expireDataTime);
            sessionList.add(session);

            return Optional.of(new SessionData(token, sessionTime));
        }
        return Optional.empty();
    }

    public boolean isAuth(Optional<String> optionalUserToken) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Iterator<Session> iterator = sessionList.iterator();

        if (optionalUserToken.isPresent()) {
            String userToken = optionalUserToken.get();
            while (iterator.hasNext()) {
                Session session = iterator.next();
                if (session.getToken().equals(userToken)) {
                    if (session.getExpireDate().isAfter(localDateTime)) {
                        return true;
                    }
                    iterator.remove();
                    break;
                }
            }
        }
        return false;
    }

    @VisibleForTesting
    boolean isValidCredential(String login, String password) {
        User user = userService.findUser(login);
        if (user != null) {
            String salt = getSalt(login);
            String encryptedPassword = passwordEncryptor.encryptPasswordWithSalt(password, salt);
            String passwordFromDB = user.getPassword();
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


