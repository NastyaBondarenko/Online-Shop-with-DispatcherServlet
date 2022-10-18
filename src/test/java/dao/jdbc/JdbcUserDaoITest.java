package dao.jdbc;

import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcUserDaoITest {
    private final JdbcUserDao jdbcUserDao = new JdbcUserDao();

    @Test
    @DisplayName("find User when User Exist then Data Return")
    public void findUser_whenUserExist_thenDataReturn() {
        User user = jdbcUserDao.findUser("user");

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
        User user = jdbcUserDao.findUser("user");

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getSalt());
        assertNotNull(user.getPassword());
        assertNotNull(user.getLogin());
    }

    @Test
    @DisplayName("find User when User Is Not Exist then throw NullPointerException ")
    public void findUser_whenUserIsNotExist_thenNullPointerExceptionThrow() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            User user = jdbcUserDao.findUser("Not_Existing_User");
            assertNull(user.getId());
            assertNull(user.getSalt());
            assertNull(user.getPassword());
            assertNull(user.getLogin());
        });
    }
}
