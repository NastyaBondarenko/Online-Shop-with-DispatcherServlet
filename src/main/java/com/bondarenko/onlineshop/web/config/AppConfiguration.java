package com.bondarenko.onlineshop.web.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan(basePackages = {"com.bondarenko.onlineshop"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.bondarenko.onlineshop.web")})
public class AppConfiguration {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/postgres");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("maria@12yarmole");
        return dataSourceBuilder.build();
    }
}
