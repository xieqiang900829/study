package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by WD42700 on 2019/2/24.
 */
public class LogProxy implements InvocationHandler {

    private Object object;

    public Object getProxyObject(Object o){
        this.object=o;
        try{
            return Proxy.newProxyInstance(this.getClass().getClassLoader(),o.getClass().getInterfaces(),this);
        }catch (IllegalArgumentException e){
            throw new RuntimeException(e);
        }

    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke ...");
        Object result= method.invoke(object,args);
        System.out.println("after invoke ...");
        return result;
    }
}
