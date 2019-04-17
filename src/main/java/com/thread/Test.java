package com.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by WD42700 on 2019/3/7.
 */
public class Test {


    public static void main(String[] args) throws InterruptedException {

        /*new A().start();

        new A().start();

        new A().start();

        new A().start();*/

        //List<String>  list = new LinkedList<>(3)  ;
        List<String>  list = new ArrayList<>(3)  ;
        list.add("111");
        list.add("222");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        System.out.println(list.size());

    }



    static class A extends Thread {

        static List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        static ThreadLocal<List<Integer>> threadLocal = new ThreadLocal<List<Integer>>() {

            @Override

            protected List<Integer> initialValue() {

                return list;

            }

        };



        @Override

        public void run() {

            List<Integer> threadList = threadLocal.get();

            threadList.add(threadList.size());

            System.out.println(threadList.toString());

        }



    }




}
