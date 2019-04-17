package com.thread;

import com.bean.PersonBase;

/**
 * Created by WD42700 on 2019/3/8.
 */
public class TestVolatile2  extends   Thread {

    private PersonBase base;

    public TestVolatile2(PersonBase  base){
        this.base = base;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
                System.out.println("hello");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sex = "+ base.getSex());
            if (base.getSex()){
                System.out.println("可以结束了");
                break;
            }
        }
    }


    public static   PersonBase personBase = new  PersonBase();

    public static void main(String[] args) throws InterruptedException {
        personBase.setSex(false);
        new TestVolatile2(personBase).start();
        Thread.sleep(3000);
        personBase.setSex(true);
        Thread.sleep(15000);
    }


}
