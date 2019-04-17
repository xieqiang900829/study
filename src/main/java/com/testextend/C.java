package com.testextend;

import java.lang.String;

/**
 * Created by WD42700 on 2019/1/27.
 */
public class C   implements  A,B {

    private  Object  friend;

    public Object getFriend() {
        return friend;
    }

    public void setFriend(Object friend) {
        this.friend = friend;
    }

    @Override
    public int add(int a, int b) {
        return 0;
    }

    public static void main(String[] args) {
        C  c1 =new C ();
        C  c2 =new C ();
        c1.setFriend(c2);
        c2.setFriend(c1);
        System.gc();

    }
}
