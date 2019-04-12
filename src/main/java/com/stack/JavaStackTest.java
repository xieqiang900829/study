package com.stack;

/**
 * Created by WD42700 on 2019/4/10.
 */
public class JavaStackTest {
    private int count = 0;

    public void testStack(){
        count++;
        testStack();

        String  a ="";
        a.intern();

    };

    /**
     *  java栈是java虚拟机的一个重要的组成部分，在栈里进行线程操作，存放方法参数等等。
     *栈在初始化过后是有一定的大小的。
     * 栈的高度称为栈的深度，栈深度受栈帧大小影响。

     我们知道，在栈中存放局部变量，参数，运行中间结果等。

     增加参数、增加局部变量 数量  会使栈的最大深度变小

     -Xss128k：设置每个线程的堆栈大小

     *
     *
     */

    public void test(){
        try {
            testStack();
        } catch (Throwable e) {
            System.out.println(e);
            System.out.println("stack height:"+count);
        }
    }

    public static void main(String[] args) {
        new JavaStackTest().test();
    }



}
