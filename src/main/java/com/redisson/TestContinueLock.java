package com.redisson;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class TestContinueLock {

    @Test
    public void  continueLock() throws Exception {
        RedissonClient client = Redisson.create();
        RLock lock = client.getLock("key2");
        boolean isLock = lock.isLocked();
        if (!isLock){
            System.out.println("获取到锁");
            lock.lock(10, TimeUnit.SECONDS);
        }
        System.out.println("开始工作");
        TimeUnit.SECONDS.sleep(12L);

        System.out.println("继续工作");
        TimeUnit.SECONDS.sleep(120L);

        lock.unlock();
        System.out.println("结束");
        System.out.println(isLock);

    }

}
