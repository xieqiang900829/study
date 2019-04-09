package com.thread;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by WD42700 on 2019/3/7.
 */
public class UseThreadLocal3 {


    static class Thread1  extends  Thread{
        int  i;
        Thread1(int i){
            super("线程"+i);
            this.i= i;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" 任务" + this.i);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5,
                10000L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(2));
        int  i=1;

        while(i<=20){
            Thread.sleep(1000);
            executor.execute(new Thread1(i));
            i++;
        }


        executor.shutdown();
        executor.shutdownNow();

        executor.getTaskCount();
    }


}
