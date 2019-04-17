package com.thread;

/**
 * Created by WD42700 on 2019/3/1.
 */
public class TestThreadMethod  {

    public static void main(String[] args) {

        Thread  a = new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("开始休眠");
                    sleep(2000);
                    System.out.println("结束休眠");
                } catch (InterruptedException e) {
                    System.out.println("休眠被中断");
                    e.printStackTrace();
                }
                ;
            }
        };
        a.start();

        try {
            Thread.sleep(5000);
            boolean  isAlive = a.isAlive();
            System.out.println("isAlive = "+isAlive);
            //Thread.currentThread().sleep();//为什么不行 、native static
            /**
             * 主线程最多等待子线程10秒钟 。如果子线程10秒钟之内还是没有完成任务。则主线程继续往下走、也可能不用等待那么久
             * 不能用wait方法  wait必须在同步代码块当中、而且调用完之后、会释放锁
             *
             * wait()、join()、sleep() 方法的区别
             */
            a.join(10000);
            System.out.println("join完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }


}
