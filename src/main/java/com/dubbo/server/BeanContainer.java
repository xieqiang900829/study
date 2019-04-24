package com.dubbo.server;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by WD42700 on 2019/4/24.
 */
public class BeanContainer {

    private static ConcurrentHashMap<Class<?>, Object> container = new ConcurrentHashMap<>();

    public static boolean addBean(Class<?> clazz, Object object) {
        container.put(clazz, object);
        return true;
    }

    public static Object getBean(Class<?> clazz) {
        return container.get(clazz);
    }

}
