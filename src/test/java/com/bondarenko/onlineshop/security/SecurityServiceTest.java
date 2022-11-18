package com.bondarenko.onlineshop.security;

import com.bondarenko.onlineshop.dto.CredentialsDto;
import com.bondarenko.onlineshop.dto.SessionDataDto;
import com.bondarenko.onlineshop.web.configuration.AppConfiguration;
import com.bondarenko.onlineshop.web.configuration.WebConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringJUnitWebConfig
@ContextConfiguration(classes = {AppConfiguration.class, WebConfiguration.class})
public class SecurityServiceTest {
    @Autowired
    private SecurityService securityService;
    private PasswordEncryptor passwordEncryptor = new PasswordEncryptor();

    @Test
    @DisplayName("test Encrypt Password With Salt")
    public void testEncryptPasswordWithSalt() {

        String actualEncryptPassword = passwordEncryptor.encryptPasswordWithSalt("pass", "4A17982C");
        String expectedEncryptPassword = "a3634ac4a752cfcee2c3e8ff2351347d";

        assertEquals(expectedEncryptPassword, actualEncryptPassword);
    }

    @Test
    @DisplayName("test Generate Salt")
    public void testGenerateSalt() {
        String actualSalt = securityService.getSalt("user");
        String expectedSalt = "4A17982C";

        assertNotNull(securityService.getSalt("user"));
        assertEquals(expectedSalt, actualSalt);
    }

    @Test
    @DisplayName("test Generate Hash")
    public void testGenerateHash() {
        String actualHash = passwordEncryptor.hash("user");
        String expectedHash = "ee11cbb19052e40b07aac0ca060c23ee";

        assertNotNull(actualHash);
        assertEquals(expectedHash, actualHash);
    }

    @Test
    @DisplayName("test Login when User And Password are Not correct")
    public void testLogin_whenUserAndPassword_areNotCorrect() {
        CredentialsDto credentialsDto = new CredentialsDto("user", "NotExistingPassword");
        Optional<SessionDataDto> sessionData = securityService.login(credentialsDto);
        assertTrue(sessionData.isEmpty());
    }

    @Test
    @DisplayName("test Login when User And Password are correct")
    public void testLogin_whenUserAndPassword_areCorrect() {
        String expectedToken = "0";
        CredentialsDto credentialsDto = new CredentialsDto("user", "pass");
        Optional<SessionDataDto> sessionData = securityService.login(credentialsDto);
        String actualToken = sessionData.get().getToken();
        assertNotNull(actualToken);
        assertNotEquals(expectedToken, actualToken);
    }

    @Test
    @DisplayName("test Check Password")
    public void testCheckPassword_whenPasswordIsValid() {
        CredentialsDto credentialsDto = new CredentialsDto("user", "pass");
        assertTrue(securityService.getUser(credentialsDto).isPresent());
    }

    @Test
    @DisplayName("test Check Password when Password Is Not Valid")
    public void testCheckPassword_whenPasswordIsNotValid() {
        CredentialsDto credentialsDto = new CredentialsDto("user", "1234");
        assertFalse(securityService.getUser(credentialsDto).isPresent());
    }

    @Test
    @DisplayName("test IsAuth False When User Not Logged In")
    void testIsAuthFalseWhenUserNotLoggedIn() {
        String userToken = String.valueOf(44566);

        assertFalse(securityService.getSession(userToken).isPresent());
    }

    @Test
    @DisplayName("test Generate Random Salt")
    public void testGenerateRandomSalt() {
        String actualSalt = passwordEncryptor.generateSalt();
        String expectedSalt = passwordEncryptor.generateSalt();

        assertNotNull(actualSalt);
        assertNotEquals(expectedSalt, actualSalt);
    }
}
