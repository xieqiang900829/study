package com.testgc;

import org.junit.Test;

import java.lang.String;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

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


      /*
      *获取垃圾收集器
      *PS Scavenge
      *
      * PS MarkSweep
       */
        List<GarbageCollectorMXBean> beans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean bean : beans) {
            System.out.println(bean.getName());
        }


    }


}
