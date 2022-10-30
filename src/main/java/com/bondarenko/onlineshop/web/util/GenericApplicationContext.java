package com.bondarenko.onlineshop.web.util;

import lombok.Setter;

@Setter
public class GenericApplicationContext {
    private static com.study.ioc.context.impl.GenericApplicationContext genericApplicationContext;

    static {
        genericApplicationContext = new com.study.ioc.context.impl.GenericApplicationContext("context.xml");
    }

    public static Object getService(String serviceName) {
        return genericApplicationContext.getBean(serviceName);
    }
}