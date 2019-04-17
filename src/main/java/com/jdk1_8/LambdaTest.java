package com.jdk1_8;

import java.lang.String;
import java.util.Arrays;
import java.util.List;

/**
 * Created by WD42700 on 2018/12/26.
 *
 * Java中Lambda表达式的使用   https://www.cnblogs.com/franson-2016/p/5593080.html
 * jdk1.8新特性  http://www.cnblogs.com/chenmc/p/9233428.html
 *
 */
public class LambdaTest {


    public static void main(String[] args) {
        String[] arr = {"aa", "bb", "cc", "dd", "ee", "ff"};
        List<String> players = Arrays.asList(arr);

        players.forEach(s -> System.out.println(s));
        players.forEach(s -> {
            System.out.println(s);
        });
        players.forEach(System.out::println);


        // 1.1使用匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world !");
            }
        }).start();

        // 1.2使用 lambda expression
        new Thread(() -> System.out.println("Hello world !")).start();

        // 2.1使用匿名内部类
        Runnable race1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world !");
            }
        };

        // 2.2使用 lambda expression
        Runnable race2 = () -> System.out.println("Hello world !");

        // 直接调用 run 方法(没开新线程哦!)
        race1.run();
        race2.run();


    }

}
