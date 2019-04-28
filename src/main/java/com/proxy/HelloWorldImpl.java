package com.proxy;

/**
 * Created by WD42700 on 2019/3/27.
 */

public class HelloWorldImpl implements HelloWorldInteface{
    public void sayHelloWorld() {
        System.out.println("hello world");
    }

    @Override
    public void write() {

    }
}
