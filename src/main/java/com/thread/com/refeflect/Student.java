package com.thread.com.refeflect;

import lombok.Data;

import java.lang.String;

/**
 * Created by WD42700 on 2018/12/12.
 */
@Data
public class Student  {

    private Integer  age  ;

    public String name;

    String  sex;

    public Student(Integer  age, String name){//没有了无参构造函数
        System.out.println("带参数的构造方法");
        this.age = age;
        this.name = name ;
    }

    public Student() {

    }

    @Override
    public String toString(){
        return "age:"+age+"  name:"+name;
    }


}
