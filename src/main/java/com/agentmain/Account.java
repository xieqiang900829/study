package com.agentmain;

public class Account {

    public void operation() {
        System.out.println("operation...");
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
