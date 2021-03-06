package com.thread;

import java.lang.String;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by WD42700 on 2018/7/17.
 */
public class TestLock {
    static Lock lock = new ReentrantLock();


    public static void main(String[] args) throws Exception{

       /* new Thread(new WorkThread1(lock)).start();

        //Thread.sleep(5000);
        System.out.println("unlock前");
        lock.unlock();
        System.out.println("unlock后");

        lock.tryLock(7, TimeUnit.SECONDS);
        System.out.println(System.currentTimeMillis()+"主线程获取了锁");*/

       ReentrantLock  lock = new ReentrantLock();//await、sign 方法  必须先获取锁。 才能调用、否则会报错 ，和 wait、notify 一样
       Condition condition = lock.newCondition();
        lock.lock();
        condition.await(2,TimeUnit.SECONDS);
       // lock.unlock();
        System.err.println("处理完成");



    }

    static class  WorkThread1 implements Runnable{
        private Lock lock;
        WorkThread1(Lock lock){
            this.lock = lock;
        }
        @Override
        public void run() {
            try {
                System.out.println(System.currentTimeMillis()+" lock 执行前");
                lock.lock();
                System.out.println(System.currentTimeMillis()+" lock 执行后");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



   /* static class  WorkThread2 implements Runnable{
        private Lock lock;
        WorkThread2(Lock lock){
            this.lock = lock;
        }
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+" wait 执行前");
            lock.lock();
            System.out.println(System.currentTimeMillis()+" wait 执行后");
        }
    }*/
}
