package com.testgc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WD42700 on 2019/3/29.
 */
public class TestFullGc {


    public static void main2(String[] args) throws InterruptedException {
        //测试老年代内存溢出
        //出现 OOME 时生成堆 dump: 禁用
        //CPU飙高的时候自动生成线程堆栈dump
        //-XX:PretenureSizeThreshold=1M
        //CommandLine flags  。fullgc 会输出 全部内存相关的参数
        System.out.println("开始处理");
        List<File>   list =  new ArrayList<> ()   ;//list 放在循环外面起到一个如果放在内存泄漏的作用  、
        while(true){
            File   f=  new File("D:\\666.xls");  //这个个500KB的 文件
            list.add(f);
            System.out.println(f.getName());
        }
    }


    public static void main(String[] args) throws InterruptedException {

        List<byte[]>   list = new ArrayList<> ()   ;
        Thread.sleep(15000);
        while (true){
            byte[] b4 = new byte[7 * 1024 * 1024];
            list.add(b4);
            System.out.println("添加一次");
        }

    }

}
