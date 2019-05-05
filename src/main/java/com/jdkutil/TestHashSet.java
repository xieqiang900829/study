package com.jdkutil;

import com.bean.Person;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by WD42700 on 2019/3/28.
 */
public class TestHashSet {
    public static void main(String[] args) {
        HashSet<Person> s = new  HashSet<>  ();
       // TreeSet a = new TreeSet<>();

        s.add(new Person(15,"zhangsan"));
        s.add(new Person(15,"zhangsan343"));




    }

}

