package com.testgc;

import org.junit.Test;

import java.lang.String;

/**
 * Created by WD42700 on 2018/12/14.
 */
public class GcTest {


    @Test
    public void  test1(){

        //String s = new  String();

      /*  int  a= 10<<2;
        int  b=a>>2;

        System.out.println(a +"  "+b);*/

        String a="abc" ;
        String b="a"+"bc";
        System.out.println(a == b);
        System.out.println();

    }


}
