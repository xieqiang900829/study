package com.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by WD42700 on 2019/2/27.
 */
public class LockThread {
    Lock lock = new ReentrantLock();

    public void lock() {
        // 尝试获取锁
        if (lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + " get the lock");
                while (true) {
                    //block here
                }
            } finally {
                // 释放锁
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " release the lock");
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " get the lock fail");
        }
    }

    public static void main(String[] args) {
        final LockThread lt = new LockThread();
        new Thread(new Runnable() {
            public void run() {
                lt.lock();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                lt.lock();
            }
        }).start();
    }
}

