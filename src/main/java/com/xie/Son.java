package com.xie;

/**
 * Created by WD42700 on 2019/4/29.
 */
public class Son extends Father{

    @Override
    public void say() {
        super.say();
    }

    @Override
    public void eat() {
        System.out.println("son is eating");
        super.eat();
    }
}
