package com.memory;

import com.thread.com.refeflect.Student;

/**
 * Created by WD42700 on 2019/1/14.
 */
public class TestString {

    public static void main(String[] args) {
        int  a= 10;
        int  b= 10;
        Integer c= 10;
        Integer d= 10;

        System.out.println(a == b);//true
        System.out.println(b == c);//true
        System.out.println(c == d);//true

        Student  s1 = new Student ();
        Student  s2 = new Student ();
        s1.setAge(10);
        s2.setAge(10);

        System.out.println(s1.getAge() == s2.getAge());
        System.out.println(d == s2.getAge());


        String str1 = "aaa";
        String str2 = "bbb";
        String str3 = "aaabbb";
        String str4 = str1 + str2;
        String str5 = "aaa" + "bbb";
        System.out.println(str3 == str4); // false
        System.out.println(str3 == str4.intern()); // true  调用intern方法，会将字符串放入常量池、并且引用常量池中的对象
        System.out.println(str3 == str5);// true

        StringBuffer  sb1 = new StringBuffer("aaa");
        StringBuffer  sb2 = new StringBuffer("aaa");
        System.out.println("StringBuffer =   "+ (sb1 == sb2));//false

    }

}
