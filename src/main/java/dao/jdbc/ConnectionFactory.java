package dao.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final String DB_URL = "db.db_url";
    private static final String JDBC_DRIVER = "db.jdbc_driver";
    private static final String USER = "db.user";
    private static final String PASS = "db.pass";

    private final Properties properties = new Properties();

    private void loadFileWithDatabaseProperties() {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(fileInputStream);
        } catch (IOException exception) {
            throw new RuntimeException("Can not load properties");
        }
    }

    public Connection connectionToDatabase() {
        loadFileWithDatabaseProperties();
        try {
            Class.forName(properties.getProperty(JDBC_DRIVER));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC_DRIVER is not running");
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection(properties.getProperty(DB_URL), properties.getProperty(USER), properties.getProperty(PASS));
        } catch (SQLException e) {
            throw new RuntimeException("Cant connect to database. Check your root, password or user`s name in database");
        }
        return connection;
    }
}

