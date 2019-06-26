package com.thread;

public class TestThreadStatus {
    final static Object obj = new Object();
    public static void main(String[] args) {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (obj){
                        System.out.println("线程1 wait");
                        obj.wait();
                        /**
                         * 线程被notify唤醒之后、会进入BLOCKER状态、等待获取对象锁。
                         * 如果不唤醒的话、是不可以参与竞争对象锁的
                         * 获取了锁之后  才可以参与锁的竞争
                         *
                         */
                        System.out.println("线程1 继续执行");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (obj){
                        System.out.println("线程3 wait");
                        obj.wait();
                        System.out.println("线程3 继续执行");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (obj){
                        System.out.println("线程2 notify");
                        obj.notifyAll();
                        System.out.println("线程2 notify之后 ， 休眠开始");
                        Thread.sleep(5000);
                        System.out.println("线程2 休眠结束");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread3.start();
        thread2.start();
    }



}
