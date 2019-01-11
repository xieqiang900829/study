package com.thread;

import com.bean.PersonBase;


/**
 * Created by WD42700 on 2019/1/6.
 */
public class TestThreadLocal {


    public static void main(String[] args) {
        PersonBase   p = new PersonBase();

       // for (int  i=0;i<50;i++){
            new Thread(new PersonThread(p)).start();
        //}


    }


    static  class   PersonThread implements Runnable{


        /**
         * Thread类又成员属性 ThreadLocal.ThreadLocalMap threadLocals = null;
         * 因为一个线程可能又多个ThreadLocal变量 、统一放在ThreadLocalMap 中，先通过线程找到 thread.threadLocalMap  ,再map.get("threadLocal对象") 获取Entry ;
         * threadLocalMap 本质和HashMap是一样的   数组 + 链表的形式
         *
         * AutoInteger 是线程安全的  本质是 volicaty  volatile关键字
         *
         * thread.join（）
         * JAVA中JOIN和WAIT的关系   https://blog.csdn.net/starryninglong/article/details/81144894
         * Java并发编程：深入剖析ThreadLocal   https://www.cnblogs.com/dolphin0520/p/3920407.html
         * DUBBO功能使用说明   https://www.cnblogs.com/php0368/p/4290791.html
         * Dubbo Filter详解 https://www.jianshu.com/p/c5ebe3e08161
         * 注解的定义和使用
         */

        private ThreadLocal<PersonBase>  person = new  ThreadLocal<> ();
        private ThreadLocal<Long>  longValue = new  ThreadLocal<> ();

        public PersonThread(PersonBase person){
            this.person.set(person);
            this.longValue.set(100L);
        }

        @Override
        public void run() {
            //person.set();
            PersonBase personBase = person.get();
            int age = personBase.getAge();
            age++;
            personBase.setAge(age);
            System.out.println(Thread.currentThread()+"  age:"+personBase.getAge());
        }
    }
}
