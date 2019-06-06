package com.thread;

import com.util.TimeUtil;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Created by WD42700 on 2019/4/18.
 */
public class TestCyclicBarrier {


    /**
     * 回环栅栏  (循环栅栏)     回环：可以循环利用，栅栏：等待后续线程完成之后一起继续往下走
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        int N = 4;
        CyclicBarrier barrier = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                System.err.println(TimeUtil.time()+" 线程" + Thread.currentThread().getName() + "大家都作做完了手上工作。。一起往下走。。。。。。。。。。。。。。。。。");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        int j = 1;
        while (j<=3){
            System.err.println("~~~~~~~~~~~~~~~~~  第"+j+"轮  ~~~~~~~~~~~~~~~~~~~~");
            for (int i = 0; i < N; i++){
                new Writer(barrier).start();
            }
            j++;
            TimeUnit.SECONDS.sleep(25);

        }
    }

    static class Writer extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            System.err.println(TimeUtil.time()+" 线程" + Thread.currentThread().getName() + "正在写入数据...");
            try {
                Thread.sleep(5000);//以睡眠来模拟写入数据操作
                int sleep = new  Random().nextInt(20);
                System.err.println(TimeUtil.time()+" 线程" + Thread.currentThread().getName() + "休眠"+sleep+" 秒");
                TimeUnit.SECONDS.sleep(sleep);
                System.err.println(TimeUtil.time()+" 线程" + Thread.currentThread().getName() + " 写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.err.println(TimeUtil.time()+"  所有线程写入完毕，继续处理其他任务...");
        }

    }

}

