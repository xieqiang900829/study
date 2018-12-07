package com.thread;

/**
 * Created by WD42700 on 2018/7/17.
 */
public class TestWaitNotify {
    public static Integer  a= 10;

    public static void main(String[] args) throws Exception{

        new Thread(new WorkThread1(a)).start();
        Thread.sleep(5000);

        new WorkThread2(a).start();

    }

    static class  WorkThread1 implements Runnable{
        private Integer  i;
        WorkThread1(Integer i){
            this.i = i;
        }
        @Override
        public void run() {
            synchronized (a){
                try {
                    System.out.println(System.currentTimeMillis()+" wait 执行前");
                    a.wait();
                    System.out.println(System.currentTimeMillis()+" wait 执行后");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }



    static class  WorkThread2 extends Thread{
        private Integer  i;
        WorkThread2(Integer i){
            this.i = i;
        }
        @Override
        public void run() {
            synchronized (a){
                try {
                    System.out.println(System.currentTimeMillis()+" 调用notifyAll");
                    a.notifyAll();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
