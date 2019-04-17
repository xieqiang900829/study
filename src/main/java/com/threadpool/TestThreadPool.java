package com.threadpool;

import java.util.concurrent.*;

/**
 * Created by WD42700 on 2019/3/8.
 */
public class TestThreadPool {

    public static void main(String[] args) throws InterruptedException {

        //最大空闲时间只是针对非核心线程的 、核心线程创建后不会再销毁。
        BlockingQueue<Runnable>  queue = new ArrayBlockingQueue(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 3, TimeUnit.SECONDS,queue);
        int poolSize = executor.getPoolSize();
        System.out.println(" poolSize=  "+poolSize);

        for(int i=0;i<50;i++){
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName()+"处理中");
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }



        poolSize = executor.getPoolSize();
        System.out.println(" poolSize=  "+poolSize);

        TimeUnit.SECONDS.sleep(15);

        poolSize = executor.getPoolSize();
        System.out.println(" poolSize=  "+poolSize);


    }

}
