package com.thread;

/**
 * Created by WD42700 on 2019/2/26.
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {
    final Lock lock = new ReentrantLock();// 锁对象
    final Condition notFull = lock.newCondition();// 写线程条件
    final Condition notEmpty = lock.newCondition();// 读线程条件

    final Integer[] items = new Integer[10];// 缓存队列
    int putptr/* 写索引 */, takeptr/* 读索引 */, count/* 队列中存在的数据个数 */;

    public void put(Integer x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                // 如果队列满了
                notFull.await();// 阻塞写线程
            items[putptr] = x;// 赋值
            System.out.println("写入：" + x);
            if (++putptr == items.length)
                putptr = 0;// 如果写索引写到队列的最后一个位置了，那么置为0
            ++count;// 个数++
            notEmpty.signal();// 唤醒读线程
        } finally {
            lock.unlock();
        }
    }

    public Integer take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                // 如果队列为空
                notEmpty.await();// 阻塞读线程
            Integer x = items[takeptr];// 取值
            System.out.println("读取：" + x);
            if (++takeptr == items.length)
                takeptr = 0;// 如果读索引读到队列的最后一个位置了，那么置为0
            --count;// 个数--
            notFull.signal();// 唤醒写线程
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final BoundedBuffer b = new BoundedBuffer();

        new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while (true) {
                    try {
                        b.put(i++);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        b.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

