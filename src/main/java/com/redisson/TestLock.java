package com.redisson;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TestLock {

    @Test
    public void  testLock() throws Exception {
        RedissonClient client = Redisson.create();
       //RLock lock = client.getLock("abcdefg");
        RLock lock = client.getFairLock("abcdefg");
       for (int  i=0;i<=50;i++){
           TimeUnit.SECONDS.sleep(1);
           final int j = i;
           new  Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("線程"+ j +"嘗試获取锁");
                    lock.lock();
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   System.err.println("線程"+j+" 释放锁");
                    lock.unlock();
                }
            }).start();
        }
        Thread.currentThread().join(1000000);
        System.err.println("-------------全部結束------------------");
        client.shutdown();
    }

    @Test
    public void  testSetAndGet() throws ExecutionException, InterruptedException {
        RedissonClient client = Redisson.create();

        RBucket<String> keyObject = client.getBucket("key5");//先获取key-value 。key不存在没关系
        keyObject.set(null);

        RAtomicLong longObject = client.getAtomicLong("myLong");
        long myLong = longObject.get();
        longObject.compareAndSet(myLong, 401);
        myLong = longObject.get();
        RFuture<Boolean>  future = longObject.compareAndSetAsync(myLong, 401);
        boolean  result = future.get();
        future.await();
    }

}
