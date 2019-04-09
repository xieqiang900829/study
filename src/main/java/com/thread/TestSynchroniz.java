package com.thread;

/**
 * Created by WD42700 on 2019/3/4.
 */
public class TestSynchroniz {

    TestWait  a= new TestWait ();

    public static void main(String[] args) {









      /*  TestSynchroniz test = new TestSynchroniz();
        test.f();*/
    }
    //nio  只是io线程从channel 中读取数据流的时候是非阻塞的。将全部数据读取完之后 提交给业务线程池，这样业务线程不用关心最耗时的网络io操作
    //io线程 在轮询读取各个通道中的数据的时候，会记录读取到了哪一个阶段。下次处于就绪状态的时候从上次的位置继续读取

    //如果后续业务比较简单 。没有数据库查询等耗时的io操作。可以不切换到业务线程池 ，而是直接给处理了。
    //dubbo 线程池有一个handler链。 上浮、下沉下的 。 装饰器模式


    public void  f(){
        System.out.println("获取类锁");
        synchronized (TestWait.class){
            synchronized (a){
                synchronized (a){
                    System.out.println("同步锁可以重入");
                }
                System.out.println("获取对象锁");
            }
        }
    }



}
