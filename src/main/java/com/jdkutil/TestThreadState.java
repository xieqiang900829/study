package com.jdkutil;

import java.lang.management.ManagementFactory;
import java.lang.String;

/**
 * Created by WD42700 on 2019/2/12.
 */
public class TestThreadState {
    public static String pid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return name.split("@")[0];
    }

    public static void main(String[] args) {
        System.out.println("进程id: "+TestThreadState.pid());
        //runnable();     // 1
        //     blocked();      // 2
             waiting();      // 3
        //     timedWaiting(); // 4
    }


    public static void runnable() {
        long i = 0;
        Long id = Thread.currentThread().getId();
        Integer temp = id.intValue();
        String threadId = Integer.toHexString(temp);
        System.out.println("线程id   16进制："+threadId+"   10进制："+id);
        while (true) {
            i++;
        }
    }

    public static void blocked() {
        final Object lock = new Object();
        new Thread() {
            public void run() {
                synchronized (lock) {
                    System.out.println("i got lock, but don't release");
                    try {
                        Thread.sleep(1000L * 1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();

        try { Thread.sleep(100); } catch (InterruptedException e) {}

        synchronized (lock) {
            try {
                Thread.sleep(30 * 1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void timedWaiting() {
        final Object lock = new Object();
        synchronized (lock) {
            try {
                lock.wait(30 * 1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void waiting() {
        final Object lock = new Object();
        synchronized (lock) {
            try {
                lock.wait();
                //使用jstack 加载类的情况、各个内存区域的情况、堆区、方法区的情况。JVM编译方法统计、总结垃圾回收统计、
            } catch (InterruptedException e) {
            }
        }
    }



}
