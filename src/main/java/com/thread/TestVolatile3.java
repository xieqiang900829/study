package com.thread;

/**
 * Created by WD42700 on 2019/3/8.
 */
public class TestVolatile3 extends   Thread {

    private int  age;

    public TestVolatile3(int  age){
        this.age = age;
    }

    @Override
    public void run() {

    }

    public static void main(String[] args)  {
        int temp = 100;

        new TestVolatile3(temp).start();
        new TestVolatile3(temp).start();
    }


}
