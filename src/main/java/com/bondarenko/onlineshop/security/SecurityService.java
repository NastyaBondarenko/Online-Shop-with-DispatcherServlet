package com.bondarenko.onlineshop.security;

import com.bondarenko.onlineshop.dto.CredentialsDto;
import com.bondarenko.onlineshop.dto.SessionDataDto;
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

    public Optional<SessionDataDto> login(CredentialsDto credentialsDto) {
        Optional<User> userOptional = getUser(credentialsDto);
        if (userOptional.isPresent()) {
            String token = passwordEncryptor.generateToken();
            LocalDateTime expireDataTime = LocalDateTime.now().plusSeconds(sessionTime);
            User user = userOptional.get();
            Session session = new Session(token, expireDataTime, user);
            sessionList.add(session);

            return Optional.of(new SessionDataDto(token, sessionTime));
        }
        return Optional.empty();
    }

    public Optional<Session> getSession(String userToken) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Iterator<Session> iterator = sessionList.iterator();

        while (iterator.hasNext()) {
            Session session = iterator.next();
            if (session.getToken().equals(userToken)) {
                if (session.getExpireDate().isAfter(localDateTime)) {
                    return Optional.of(session);
                }
                iterator.remove();
                break;
            }
        }
        return Optional.empty();
    }

    @VisibleForTesting
    String getSalt(String login) {
        User userLogin = userService.findUser(login);
        return userLogin.getSalt();
    }

    @VisibleForTesting
    Optional<User> getUser(CredentialsDto credentialsDto) {//refactor isValidCredential
        String login = credentialsDto.getLogin();
        User user = userService.findUser(login);
        if (user != null) {
            String salt = getSalt(login);
            String password = credentialsDto.getPassword();
            String encryptedPassword = passwordEncryptor.encryptPasswordWithSalt(password, salt);
            String passwordFromDB = user.getPassword();
            if (Objects.equals(encryptedPassword, passwordFromDB)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @VisibleForTesting
    boolean isValidCredential(String login, String password) {//Optional
        User user = userService.findUser(login);
        if (user != null) {
            String salt = getSalt(login);
            String encryptedPassword = passwordEncryptor.encryptPasswordWithSalt(password, salt);
            String passwordFromDB = user.getPassword();
            return Objects.equals(encryptedPassword, passwordFromDB);
        }
        return false;
    }
}

