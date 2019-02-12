package com.memory;

import com.thread.com.refeflect.Student;

import java.lang.String;
import java.util.List;

/**
 * Created by WD42700 on 2019/1/11.
 */
public class Solution {

    //基本类型的参数传递
    public static void fun1(int m){
        m = 100;
    }

    //参数为对象，不改变引用的值
    public static void fun2(StringBuffer s){
        s.append("fun2");
    }

    //参数为对象，改变引用的值
    public static void fun3(StringBuffer s){
        /**
         * 传递的只是引用的拷贝、在调用方法内部 让引用指向了新的对象之后、对方法外部的引用变量没有印象
         * 但是如果return 则会将外面的引用变量修改、会让他指向新的对象。
         * 因为 return  其实是返回的是新对象的地址
         *
         *
         */
        s = new StringBuffer("fun3");
    }

    public static void main(String[] args) {

        /**
         *
         *
         * Java中对象的赋值与引用
         * https://blog.csdn.net/yz930618/article/details/76278997/
         *
         *
         *
         *
         * 以上结果中，fun1方法的参数是基本类型，尽管在fun1中参数m的值发生了改变，但是并不影响m。

         fun2方法的参数是一个对象，当把ss传给参数s时，s得到的是ss的拷贝，所以s和ss指向的是同一个对象，因此，使用s操作对象，ss也会受影响。

         fun3方法的参数虽然也是一个对象，当ss传给参数s时，s得到的是ss的拷贝，但是，在fun3中改变了s指向的对象，给s重新赋值后，s与ss已经没有关系，它和ss指向了不同的对象，所以不管对s做什么操作，ss都不会受影响。

         */

        int i = 1;
        Solution s1 = new Solution();

        fun1(i);
        System.out.println(i);//i = 1

        StringBuffer ss = new StringBuffer("main");

        System.out.println(ss.toString());//main

        fun2(ss);
        System.out.println(ss.toString());//mainfun2

        fun3(ss);
        System.out.println(ss.toString());//mainfun2

        List<Student> list = findById(10, Student.class);
    }




    public static  <T>   List<T>  findById(Integer   id,Class<T>  cls){

        return null;

    }
}
