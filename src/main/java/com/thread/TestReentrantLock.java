package com.thread;


import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by WD42700 on 2019/5/9.
 */
public class TestReentrantLock {

    @Test
    public void f(){
        ReentrantLock lock = new  ReentrantLock();
        lock.lock();
        System.err.println("加锁成功");

        lock.lock();
        System.err.println("再次加锁成功");


    }

}
