package com.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by WD42700 on 2019/3/8.
 */
public class TestVolatile3 extends   Thread {

    private boolean  sex;

    public TestVolatile3(boolean  sex){
        this.sex = sex;
    }

    @Override
    public void run() {
        while(sex){
            System.out.println("处理中.............");
        }
        System.out.println(".................................over......................................");
    }

     volatile static boolean temp = true;

    public static void main(String[] args) throws Exception {
        new TestVolatile3(temp).start();
        TimeUnit.SECONDS.sleep(2);


        new  Thread(new Runnable() {
            @Override
            public void run() {
                temp = false;
            }
        }).start();

    }


}
