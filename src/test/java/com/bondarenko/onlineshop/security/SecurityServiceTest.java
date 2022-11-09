package com.bondarenko.onlineshop.security;

import com.bondarenko.onlineshop.entity.SessionData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitWebConfig
@ContextConfiguration(locations = "classpath:WEB-INF/context.xml")
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
        SessionData sessionData = securityService.login("user", "NotExistingPassword");
        assertNull(sessionData);
    }

    @Test
    @DisplayName("test Login when User And Password are correct")
    public void testLogin_whenUserAndPassword_areCorrect() {
        String expectedToken = "0";
        SessionData sessionData = securityService.login("user", "pass");
        String actualToken = sessionData.getToken();
        assertNotNull(actualToken);
        assertNotEquals(expectedToken, actualToken);
    }

    @Test
    @DisplayName("test Check Password")
    public void testCheckPassword_whenPasswordIsValid() {
        assertTrue(securityService.isValidCredential("user", "pass"));
    }

    @Test
    @DisplayName("test Check Password when Password Is Not Valid")
    public void testCheckPassword_whenPasswordIsNotValid() {
        assertFalse(securityService.isValidCredential("user", "1234"));
    }

    @Test
    @DisplayName("test Is Auth False when Cookies Is Null")
    public void testIsAuthFalse_whenCookiesIsNull() {
        Cookie[] cookies = null;
        assertFalse(securityService.isAuth(cookies));
    }

    @Test
    @DisplayName("test IsAuth False When User Not Logged In")
    void testIsAuthFalseWhenUserNotLoggedIn() {
        Cookie[] cookies = new Cookie[0];

        assertFalse(securityService.isAuth(cookies));
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
