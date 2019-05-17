package com.thread;

import com.util.TimeUtil;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestThreadStateAndJoin {

    @Test
    public  void   testSleepState() throws InterruptedException {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(a.getState());
        a.start();
        System.out.println(a.getState());
        TimeUnit.SECONDS.sleep(3);
        System.out.println(a.getState());

        /*
        输出結果：
        NEW
         RUNNABLE
        TIMED_WAITING*/

    }


    public void    testJoin(){
        //https://www.cnblogs.com/reality-soul/p/7871781.html

    }

}
