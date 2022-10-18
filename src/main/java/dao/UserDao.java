package dao;

import entity.User;

public interface UserDao {
    User findUser(String login);
}
