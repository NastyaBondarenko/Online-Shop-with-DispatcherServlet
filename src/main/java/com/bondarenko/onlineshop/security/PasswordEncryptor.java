package com.bondarenko.onlineshop.security;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.codec.digest.DigestUtils;

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
    String generateToken() {
        return UUID.randomUUID().toString();
    }
}
