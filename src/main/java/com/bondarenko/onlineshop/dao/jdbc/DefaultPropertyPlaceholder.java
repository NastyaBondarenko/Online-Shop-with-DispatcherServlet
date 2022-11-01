package com.bondarenko.onlineshop.dao.jdbc;

import com.study.ioc.entity.BeanDefinition;
import com.study.ioc.processor.BeanFactoryPostProcessor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public class DefaultPropertyPlaceholder implements BeanFactoryPostProcessor {
    private static final String DB_URL = "db.db_url";
    private static final String USER = "db.user";
    private static final String PASS = "db.pass";
    private final Properties properties = new Properties();

    @Override
    public void postProcessBeanFactory(Map<String, BeanDefinition> beanDefinitionMap) {

        for (Map.Entry<String, BeanDefinition> entryBeanDefinition : beanDefinitionMap.entrySet()) {
            BeanDefinition beanDefinition = entryBeanDefinition.getValue();
            Map<String, String> valueDependencies = beanDefinition.getValueDependencies();
            for (Map.Entry<String, String> entryValueDependencies : valueDependencies.entrySet()) {
                loadFileWithDatabaseProperties();

                String value = entryValueDependencies.getValue();
                value = getProperty(value);
                valueDependencies.put(entryValueDependencies.getKey(), value);
            }
        }
    }

    private String getProperty(String value) {
        if (value.equals("${db.pass}")) {
            return properties.getProperty(PASS);
        }
        if (value.equals("${db.user}")) {
            return properties.getProperty(USER);
        }
        if (value.equals("${db.databaseName}")) {
            String property = properties.getProperty(DB_URL);
            return Stream.of(property.split("((?<=[:/]))"))
                    .sorted((a, b) -> -1).findFirst().get();
        }
        String portNumber = getPortNumber(value);
        if (portNumber != null) return portNumber;
        return value;
    }

    private String getPortNumber(String value) {
        if (value.equals("${db.portNumber}")) {
            String property = properties.getProperty(DB_URL);
            String portNumber = Stream.of(property.split("((?<=[://]))"))
                    .collect(ArrayList::new, (list, e) -> list.add(0, e),
                            (list1, list2) -> list1.addAll(0, list2))
                    .stream().skip(1)
                    .findFirst().get().toString();
            if (portNumber.endsWith("/")) {
                portNumber = portNumber.substring(0, portNumber.length() - 1);
            }
            return portNumber;
        }
        return null;
    }

    private void loadFileWithDatabaseProperties() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream =
                new BufferedInputStream(classLoader.getResourceAsStream("application.properties"));
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Can not load properties", e);
        }
    }
}