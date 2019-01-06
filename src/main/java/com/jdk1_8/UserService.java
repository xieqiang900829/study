package com.jdk1_8;

/**
 * Created by WD42700 on 2018/12/26.
 */
public interface UserService {


    /**
     * 修改jdk版本，改两个地方  pom.xml ，edit Configuration【修改pom文件即可】
     * 1.7不支持这种写法
     * 1.8可以 default 关键字不可少
     * 在jdk1.8之前，接口中只允许有抽象方法，但是在1.8之后，接口中允许有一个非抽象的方法，但是必须使用default进行修饰，叫做扩展方法。
     *  在我们实现接口之后，可以选择对方法直接使用或者重写。
     *
     */
    void deleteUser();

    default void showUser(){
        System.out.println("show User");
    }


    default void showUser2(){
        System.out.println("show User");
    }

    default void showUser3(){
        System.out.println("show User");
    }

}
