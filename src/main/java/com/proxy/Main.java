package com.proxy;

import java.awt.image.WritableRaster;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by WD42700 on 2019/3/27.
 */
public class Main {

    public static void main(String[] args) {

        /**
         *
         *谈谈字节码增强(一)之java动态代理
         *
         * https://www.jianshu.com/p/0f64779cdcea
         *
         */
        System.out.println("main_method input param:"+args);
        LogProxy logProxy = new LogProxy();
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        HelloWorldInteface helloWorldInteface = (HelloWorldInteface) logProxy.getProxyObject(new HelloWorldImpl());
        helloWorldInteface.sayHelloWorld();

        //代理没有实现类的接口、或者实现类不在当前进程内
        WriteInterface writeInterface = (WriteInterface) Proxy.newProxyInstance(WriteInterface.class.getClassLoader(), new Class[]{WriteInterface.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();
                System.err.println("方法名称"+name);
                //method.invoke(null,args);这个没有办法执行 、因为只有接口。没有实例独享
                return null;
            }
        });
        writeInterface.isWrite();
        writeInterface.read();

        /**
         * 返回类自己继承的接口WriteParentInterface,WriteParentInterface2
         * 如果没有继承接口 返回的是空数组、长度为0。例如没继承接口的接口或者实现类
         * 只返回直接继承的、不包括父接口继承的
         */
        Class<?>[]  arr = WriteInterface.class.getInterfaces();
        Class<?>[]  arr2 = WriteParentInterface2.class.getInterfaces();
        Class<?>[]  arr3 = Main.class.getInterfaces();
        Class<?>[]  arr4 = WriteImpl.class.getInterfaces();
    }

}
