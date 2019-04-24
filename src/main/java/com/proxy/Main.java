package com.proxy;

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
        helloWorldInteface.sayHello();
    }

}
