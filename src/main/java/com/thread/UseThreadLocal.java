package com.thread;

/**
 * Created by WD42700 on 2019/3/7.
 */
public class UseThreadLocal {

    /**
     * 一个
     */
    public static  final ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return super.initialValue();
        }
    } ;


    public static void main(String[] args) {
        String hello = "hello";
        threadLocal.set(hello);
        new Thread(new Runnable(){
            @Override
            public void run() {
                threadLocal.set("你好!");
                System.out.println(Thread.currentThread().getName()+" ："+threadLocal.get());
                new UseThreadLocal2().f();
            }
        }).start();
        System.out.println(Thread.currentThread().getName()+" ："+threadLocal.get());
        new UseThreadLocal2().f();

      //  UseThreadLocal.threadLocal.
    }

}
