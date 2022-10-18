package dao.jdbc.mapper;

import entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {
    @Test
    @DisplayName("test User Row Mapper")
    public void testUserRowMapper() throws SQLException {
        UserRowMapper userRowMapper = new UserRowMapper();
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("login")).thenReturn("user");
        when(resultSet.getString("password")).thenReturn("pass");
        when(resultSet.getString("salt")).thenReturn("4A17982d");

        User actualUser = userRowMapper.mapRow(resultSet);

        assertNotNull(actualUser);
        assertEquals(1, actualUser.getId());
        assertEquals("user", actualUser.getLogin());
        assertEquals("pass", actualUser.getPassword());
        assertEquals("4A17982d", actualUser.getSalt());
    }
}
