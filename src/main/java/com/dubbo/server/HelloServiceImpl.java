package com.dubbo.server;

/**
 * Created by WD42700 on 2019/4/24.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}