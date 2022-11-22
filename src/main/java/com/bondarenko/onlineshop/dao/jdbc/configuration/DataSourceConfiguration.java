package com.bondarenko.onlineshop.dao.jdbc.configuration;

import com.bondarenko.onlineshop.dao.jdbc.jdbcTemplate.JdbcTemplate;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfiguration {
    @Value("${db.password}")
    private String password;
    @Value("${db.username}")
    private String user;
    @Value("${db.url}")
    private String url;

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}