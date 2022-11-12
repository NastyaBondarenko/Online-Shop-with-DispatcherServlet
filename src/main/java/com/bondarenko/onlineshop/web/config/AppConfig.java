package com.bondarenko.onlineshop.web.config;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:application.properties"})

@ComponentScan(basePackages = "com.bondarenko.onlineshop")
public class AppConfig {

    @Bean
    public DataSource restDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("maria@12yarmole");
        return dataSource;
    }
}
