package com.memory;

import com.thread.com.refeflect.Student;

import java.lang.String;

/**
 * Created by WD42700 on 2019/1/10.
 */
public class TestMemory {

    public static void main1(String[] args) {
        Student   s = new Student();
        s.setAge(15);

        test(s);
        System.out.println(s.getAge());
    }

    public static void  test(Student   s){
        s = new  Student();
        s.setAge(66);
    }

    public static void main2(String[] args) {
        Student   s = new Student();
        s.setAge(15);

        Student  s1 = s;//因为没有new 新对象、用的还是同一个
        s1.setAge(66);

        System.out.println(s.getAge()+" ：  "+s1.getAge());//输出: 66 ：  66
    }

    public static void main3(String[] args) {
        Student   s = new Student();
        s.setAge(15);

        Student  s1 = new Student();//因为没有new 新对象、用的还是同一个
        s1.setAge(66);
        s = s1;

        System.out.println(s.getAge()+" ：  "+s1.getAge());//输出: 66 ：  66 相当于方法调用之后、在这方法返回
    }


    public static void main(String[] args) {
        Student   s = new Student();
        s.setAge(15);

        Student  s1 = s;//因为没有new 新对象、用的还是同一个
        s1 = new Student();
        s1.setAge(66);

        System.out.println(s.getAge()+" ：  "+s1.getAge());//输出: 15 ：  66    只是在堆区开辟了一块新地址 、修改的是新地址中的东西，老地址 的值并没有变化
    }

}
