package com.dubbo.client;

import java.lang.reflect.Proxy;

/**
 * Created by WD42700 on 2019/4/24.
 */
public class RpcProxyFactory {
    /**
     * 多线程环境代理对象
     *
     * @param interfaceClass
     * @return T
     * @createTime：2018/7/1
     * @author: xieqiang
     */
    public static <T> T getMultService(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass },
                new RpcNIoMultHandler());
    }
}
