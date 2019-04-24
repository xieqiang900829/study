package com.blockqueue;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by WD42700 on 2019/4/18.
 */
public class TestSynchronousQueue {

    private static SynchronousQueue<String> queue = new SynchronousQueue<String>();

    public static void main(String[] args) throws Exception{
        new Productor(1).start();
        new Productor(2).start();

        Thread.sleep(200);
        new Consumer().start();

        System.out.println("main over.");
    }

    static class Productor extends Thread{
        private int id;

        public Productor(int id)
        {
            this.id = id;
        }

        @Override
        public void run()
        {
            try
            {
                String result = "id=" + this.id;
                System.out.println("begin to produce."+result);
                queue.put(result);
                System.out.println("success to produce."+result);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {
        @Override
        public void run()
        {
            try
            {
                System.out.println("consume begin.");
                String v = queue.take();
                System.out.println("consume success." + v);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

}
