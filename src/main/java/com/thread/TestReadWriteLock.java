package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by WD42700 on 2019/2/28.
 */
public class TestReadWriteLock extends   Thread{

    //ReadWriteLock管理一组锁，一个是只读的锁，一个是写锁
    static ReadWriteLock  lock = new ReentrantReadWriteLock();

    public void run2() {
        try {
            lock.writeLock().lock(); //ReentrantLock
            int i = 0;
            while (i <= 10) {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() +"开始处理  "+ (++i));
            }
        }catch(Exception e){
          e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
    }



    public static void main2(String[] args) {
        /*new TestReadWriteLock().start();
        new TestReadWriteLock().start();
        new TestReadWriteLock().start();*/

       /* lock.writeLock().lock();
        System.out.println("获取写锁");

        lock.readLock().lock();
        System.out.println("获取读锁");*/

        //同一个线程 先获取写锁、再获取读锁 是没有问题的
        //同一个线程 先获取读锁、再获取写锁 获取不到会一直阻塞



      /*  lock.writeLock().lock();
        System.out.println("获取写锁");*/

        lock.readLock().lock();
        System.out.println("获取读锁");

        new Thread(){
            @Override
            public void run() {
                lock.readLock().lock();
                System.out.println("子线程获取写锁");
            }
        }.start();



    }


    public static void main(String[] args) {
        //同时读、写
        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(new Runnable() {
            @Override
            public void run() {
                writeFile(Thread.currentThread());
            }
        });

        service.execute(new Runnable() {
            @Override
            public void run() {
                readFile(Thread.currentThread());
            }
        });
    }

    // 读操作
    public static void readFile(Thread thread) {
        lock.readLock().lock();
     /*   boolean readLock = lock.isWriteLocked();
        if (!readLock) {
            System.out.println("当前为读锁！");
        }*/
        try {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread.getName() + ":正在进行读操作……");
            }
            System.out.println(thread.getName() + ":读操作完毕！");
        } finally {
            System.out.println("释放读锁！");
            lock.readLock().unlock();
        }
    }

    // 写操作
    public static void writeFile(Thread thread) {
        lock.writeLock().lock();
        /*boolean writeLock = lock.isWriteLocked();
        if (writeLock) {
            System.out.println("当前为写锁！");
        }*/
        try {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread.getName() + ":正在进行写操作……");
            }
            System.out.println(thread.getName() + ":写操作完毕！");
        } finally {
            System.out.println("释放写锁！");
            lock.writeLock().unlock();
        }
    }

}
