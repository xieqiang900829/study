package com.thread;

/**
 * Created by WD42700 on 2019/2/24.
 */

import java.util.concurrent.atomic.AtomicInteger;

public class TestAutoInteger {

    public static void main(String[] args) {

        //Condition
        AtomicInteger  aotmic  =new  AtomicInteger(0);
        aotmic.incrementAndGet();


        System.out.println(aotmic);

    }

}
