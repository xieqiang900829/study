package com.redisson;

import jodd.datetime.TimeUtil;
import org.junit.Test;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 测试事务ScanOptions
 */
public class TestTransaction {
    @Test
    public void test2Trans() throws Exception {
        Jedis jedis = new Jedis("localhost");
        long start = System.currentTimeMillis();
        //ERR WATCH inside MULTI is not allowed
        jedis.watch("a","b","c");//当监控的健变化时候，不会抛异常 会discard停止事务的执行
        Transaction tx = jedis.multi();
        for (int i = 0; i < 50; i++) {
           /* if(i==50){
                System.err.println("开始休眠");
                TimeUnit.SECONDS.sleep(15L);
            }*/
            String temp = tx.set("t" + i, "t" + i);
           // System.out.println("temp = "+temp);//返回的结果是QUEUED
            String a = tx.get("a");
            System.out.println("a = "+a);//返回的结果也是QUEUED
        }
        List<Object> results = tx.exec();
        long end = System.currentTimeMillis();
        System.out.println("Transaction SET: " + ((end - start)/1000.0) + " seconds");
        jedis.disconnect();
    }

    @Test
    public void test3Pipelined() throws Exception {
        Jedis jedis = new Jedis("localhost");
        Pipeline pipeline = jedis.pipelined();//不同步等待其返回结果。
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("p" + i, "p" + i);
        }
      //  List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined SET: " + ((end - start)/1000.0) + " seconds");
        jedis.disconnect();
    }

   /* @Test
    public void test4combPipelineTrans() {
        Jedis jedis = new Jedis("localhost");
        long start = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        pipeline.multi();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("" + i, "" + i);
        }
        pipeline.exec();
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined transaction: " + ((end - start)/1000.0) + " seconds");
        jedis.disconnect();
    }*/

    @Test
    public void test5shardNormal() throws IOException {
        List<JedisShardInfo> shards = Arrays.asList(
              //  new JedisShardInfo("localhost",6380),
                new JedisShardInfo("localhost",6379));

        ShardedJedis sharding = new ShardedJedis(shards);
        //分布式集群直连 、通过hash(key)确认分片
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String result = sharding.set("sn" + i, "n" + i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Simple@Sharing SET: " + ((end - start)/1000.0) + " seconds");

        sharding.disconnect();
    }

  /*  @Test
    public void test6shardpipelined() throws IOException {
        List<JedisShardInfo> shards = Arrays.asList(
                new JedisShardInfo("localhost",6379),
                new JedisShardInfo("localhost",6380));

        ShardedJedis sharding = new ShardedJedis(shards);


        ShardedJedisPipeline pipeline = sharding.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("sp" + i, "p" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined@Sharing SET: " + ((end - start)/1000.0) + " seconds");

        sharding.disconnect();
    }*/



}
