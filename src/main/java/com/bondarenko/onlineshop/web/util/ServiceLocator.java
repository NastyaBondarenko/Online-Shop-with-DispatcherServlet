package com.bondarenko.onlineshop.web.util;

import com.study.ioc.context.impl.GenericApplicationContext;
import lombok.Setter;

@Setter
public class ServiceLocator {
    private static GenericApplicationContext genericApplicationContext;

    static {
        genericApplicationContext = new GenericApplicationContext("context.xml");
    }

    public static Object getService(String serviceName) {
        return genericApplicationContext.getBean(serviceName);
    }
}