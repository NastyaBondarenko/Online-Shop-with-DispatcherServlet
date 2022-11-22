package com.bondarenko.onlineshop.dao.jdbc;

import com.bondarenko.onlineshop.entity.User;
import com.bondarenko.onlineshop.web.configuration.AppConfiguration;
import com.bondarenko.onlineshop.web.configuration.WebConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig
@ContextConfiguration(classes = {AppConfiguration.class, WebConfiguration.class})
public class JdbcUserDaoITest {
    @Autowired
    private JdbcUserDao jdbcUserDao;

    @Test
    @DisplayName("find User when User Exist then Data Return")
    public void findUser_whenUserExist_thenDataReturn() {
        User user = jdbcUserDao.findUser("user").get();

        int actualId = user.getId();
        int expectedId = 1;

        String actualSalt = user.getSalt();
        String expectedSalt = "4A17982C";

        String actualPassword = user.getPassword();
        String expectedPassword = "a3634ac4a752cfcee2c3e8ff2351347d";

        assertEquals(expectedId, actualId);
        assertEquals(expectedSalt, actualSalt);
        assertEquals(expectedPassword, actualPassword);
    }

    @Test
    @DisplayName("find User when User Exist then Return Not Null Data")
    public void findUser_whenUserExist_thenReturnNotNullData() {
        User user = jdbcUserDao.findUser("user").get();

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getSalt());
        assertNotNull(user.getPassword());
        assertNotNull(user.getUser());
    }

    @Test
    @DisplayName("find User when User Is Not Exist then throw NullPointerException ")
    public void findUser_whenUserIsNotExist_thenNullPointerExceptionThrow() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            User user = jdbcUserDao.findUser("Not_Existing_User").get();
            assertNull(user.getId());
            assertNull(user.getSalt());
            assertNull(user.getPassword());
            assertNull(user.getUser());
        });
    }
}
