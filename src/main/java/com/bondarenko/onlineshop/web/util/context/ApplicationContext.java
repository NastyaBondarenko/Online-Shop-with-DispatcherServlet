package com.bondarenko.onlineshop.web.util.context;

import com.study.ioc.context.impl.GenericApplicationContext;
import lombok.Setter;

@Setter
public class ApplicationContext {
    private static GenericApplicationContext genericApplicationContext;

    static {
        genericApplicationContext = new GenericApplicationContext("context.xml");
    }

    public static Object getService(String serviceName) {
        return genericApplicationContext.getBean(serviceName);
    }
}