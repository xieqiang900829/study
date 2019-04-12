package com.proxy;

/**
 * Created by WD42700 on 2019/3/27.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("输入参数："+args);
        LogProxy logProxy = new LogProxy();
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        HelloWorldInteface helloWorldInteface = (HelloWorldInteface) logProxy.getProxyObject(new HelloWorldImpl());
        helloWorldInteface.sayHello();
    }

}
