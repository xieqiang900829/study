package com.javaagent;


import java.util.ArrayList;
import java.util.List;

public class Demo {

    private int  sex ;
    private String name;
    public Integer add(int a,int  b){
        List<Integer> list =new ArrayList<>();
        list.add(a+b);
        System.err.println("方法处理中");
        return 100;
    }

    public void print(){
        System.out.println(100);
    }
}
