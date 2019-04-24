package com.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * Created by WD42700 on 2019/4/18.
 *
 * https://www.jianshu.com/p/53da4e746657
 *
 */
public class VisibilityTest {

    private static boolean lockStatus = false;

    public void changeLockStatus() {
        lockStatus = !lockStatus;
    }

    public void printResult(int i) {

        for (; ;) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (lockStatus) {
                System.err.println(Thread.currentThread().getName()+"-- lock = " + lockStatus);
            } else {
                System.out.println(Thread.currentThread().getName() +"-- lock = "+ lockStatus);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        VisibilityTest test = new VisibilityTest();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("person-[%d]").build();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 8, 5L,
                TimeUnit.SECONDS, new SynchronousQueue<>(true),
                threadFactory, handler);

        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 5; i++) {
            final  int index = i;
            Runnable task = () -> {
                try {
                    latch.await();//导致当前线程等到锁存器计数到零，除非线程是 interrupted 。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.printResult(index);
            };
            pool.execute(task);
        }

        latch.countDown();//减少锁存器的计数，如果计数达到零，释放所有等待的线程。
        System.out.println("start task");
        //改变锁状态观察线程可见性
        TimeUnit.SECONDS.sleep(6);
        pool.execute(test::changeLockStatus);

        TimeUnit.SECONDS.sleep(6);
        pool.execute(test::changeLockStatus);

        pool.shutdown();

    }



}
