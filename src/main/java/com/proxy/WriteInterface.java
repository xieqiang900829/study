package com.proxy;

//可以继承多个接口、但是只能继承一个类
public interface WriteInterface extends WriteParentInterface,WriteParentInterface2 {

    public void isWrite();

    public void read();
}
