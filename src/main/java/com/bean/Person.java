package com.bean;

import lombok.Data;

import java.util.HashSet;

/**
 * Created by WD42700 on 2019/3/12.
 */

@Data
public class Person {

    private  int  age ;

    private String name ;

    public Person(int  age,String name ){
        this.age =age;
        this.name =name;
    }

    @Override
    public int hashCode() {
        System.out.println("执行hashCode方法");
        return age;
    }


    @Override
    public boolean equals(Object obj) {
        System.out.println("执行equals方法");
        Person  p = (Person)obj;
        if (this.age == p.age  && p.name.equals(this.name)){
            return true;
        }else{
            return false;
        }
    }


    public static void main(String[] args) {
        HashSet<Person>  set = new HashSet<>();

        Person a =new Person(15,"aaa");
        Person b =new Person(15,"bbb");
        set.add(a);
        set.add(b);

    }


}
