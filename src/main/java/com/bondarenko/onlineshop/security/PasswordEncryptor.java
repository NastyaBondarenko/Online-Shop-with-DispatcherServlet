package com.bondarenko.onlineshop.security;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Cookie;
import java.util.UUID;

public class PasswordEncryptor {
    @VisibleForTesting
    String encryptPasswordWithSalt(String password, String salt) {
        String passwordWithSalt = password + salt;
        return hash(passwordWithSalt);
    }

    @VisibleForTesting
    String hash(String value) {
        return DigestUtils.md5Hex(value);
    }

    @VisibleForTesting
    String generateSalt() {
        return UUID.randomUUID().toString();
    }

    @VisibleForTesting
    Cookie generateCookie() {
        String userToken = UUID.randomUUID().toString();
        return new Cookie("user-token", userToken);
    }
}
