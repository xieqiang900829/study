package com.thread;

import java.sql.Driver;
import java.sql.DriverManager;

/**
 * Created by WD42700 on 2019/2/24.
 */
public class TestWait {

    public static  boolean available = true;
    public static void main(String[] args) throws Exception {
        ClassLoader  loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);//AppClassLoader 系统类加载器

        ClassLoader  loader1 = DriverManager.class.getClassLoader();
        System.out.println(loader1);//null 引导类加载器


        /*Object o = new Object();

        new Thread1(o).start();
        //new Thread1(o).start();

        Thread.sleep(5000);*/
    }

    static   class  Thread1  extends Thread{

        private Object o;
        public  Thread1(Object o){
            this.o = 0 ;
        }
        @Override
        public void run() {
            synchronized(o){
                System.out.println(currentThread().getName()+" 开始处理");
                try {
                    ClassLoader  loader = Thread.currentThread().getContextClassLoader();
                    System.out.println(loader);

                    ClassLoader  loader1 = DriverManager.class.getClassLoader();
                    System.out.println(loader1);

                    loader.loadClass("");

                    Thread.sleep(1000);
                    /**
                     * this.wait() 会报异常 IllegalMonitorStateException。
                     * 调用wait方法之后、会马上释放锁，同步区域里面的代码不会继续执。
                     * 等到被notify的时候在继续往下执行
                     */
                    if (Thread.currentThread().getName().equals("Thread-0")){
                        o.wait();
                    }else{
                        o.notify();
                    }

                    /**
                     * 因此，其他线程虽被唤醒，但是仍无法获得锁。直到该线程退出synchronizedsynchronized这个方法（即执行完），释放锁后，其他线程才有机会去抢夺，去获得锁继续执行。
                     * notify() 只是去通知其他的线、并没有释放锁，但是synchronized 方法里面的代码还是会执行完毕的。
                     * 这个notify其实没有意义
                     */
                    //o.notify(); //


                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(currentThread().getName()+" 结束处理");
            }
            System.out.println(currentThread().getName()+" 完美结束");
        }


    }
}
