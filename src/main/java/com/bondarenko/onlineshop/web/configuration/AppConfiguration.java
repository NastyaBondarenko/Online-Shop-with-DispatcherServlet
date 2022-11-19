package com.bondarenko.onlineshop.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan(basePackages = {"com.bondarenko.onlineshop"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.bondarenko\\.onlineshop\\.web..*")})
public class AppConfiguration {

}