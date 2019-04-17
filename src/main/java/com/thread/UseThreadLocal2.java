package com.thread;

/**
 * Created by WD42700 on 2019/3/7.
 */
public class UseThreadLocal2 {

    public void  f(){
        String  temp = UseThreadLocal.threadLocal.get();
        System.out.println(Thread.currentThread().getName()+" ==== "+temp);
        UseThreadLocal.threadLocal.remove();
    }
}
