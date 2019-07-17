package com.thread;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by WD42700 on 2019/7/3.
 */
public class TestThreadFactory {

    @Test
    public void   f1(){
        Date startDate = new Date();
        System.out.println(DateUtils.addMinutes(startDate,-5));
    }

    public static void main(String[] args) {

        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"auctionMessageThread");
            }
        };
          ThreadPoolExecutor pool =new ThreadPoolExecutor(1,1,30, TimeUnit.SECONDS,new LinkedBlockingQueue(1),factory, new RejectedExecutionHandler() {
              @Override
              public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                  System.err.println("竞价短信任务队列已满，拒绝执行");

              }
          });//这个不可以
          //ThreadPoolExecutor pool2 =new ThreadPoolExecutor(5,10,30, TimeUnit.SECONDS,new LinkedBlockingQueue(10));//这个可以
        for (int j=0;j<100;j++){
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i=0;i<=10;i++){
                pool.submit(new Runnable() {
                    @Override
                    public void run() {
                        Thread thread = Thread.currentThread();
                        System.out.println("线程名称： "+thread.getName()+"  线程id："+thread.getId() +"执行任务");
                    }
                });
            }
        }

    }


}
