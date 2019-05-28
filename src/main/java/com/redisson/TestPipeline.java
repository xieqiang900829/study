package com.redisson;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBatch;
import org.redisson.api.RedissonClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.xml.ws.Response;
import java.util.*;

/**
 * 测试管道
 */
public class TestPipeline {

    @Test
    public void test0(){
        String redisIp = "127.0.0.1";
        int reidsPort = 6379;
        Jedis jedis = new Jedis(redisIp, reidsPort);

        long start = System.currentTimeMillis();
        for (int i=0;i<100000;i++){
            jedis.set("key"+i,String.valueOf(i));
            jedis.get("key"+i);
        }
        long end = System.currentTimeMillis();
        System.err.println("非管道耗时: " + ((end - start)/1000.0) + " seconds");//非管道耗时: 22.983 seconds
    }

    @Test
    public void test1(){
        String redisIp = "127.0.0.1";
        int reidsPort = 6379;
        Jedis jedis = new Jedis(redisIp, reidsPort);
        Pipeline pl = jedis.pipelined();

        long start = System.currentTimeMillis();
        for (int i=0;i<100000;i++){
            pl.set("key"+i,String.valueOf(i));
            pl.get("key"+i);
        }
        long end = System.currentTimeMillis();
        System.err.println("管道耗时: " + ((end - start)/1000.0) + " seconds");//管道耗时: 11.237 seconds
    }

     @Test
     public  void testJedis(){
         String redisIp = "127.0.0.1";
         int reidsPort = 6379;
         Jedis jedis = new Jedis(redisIp, reidsPort);
         Pipeline pl = jedis.pipelined();

         HashMap<String, Response<String>> map = new HashMap<String,Response<String>>(20);
         HashMap<String, Response<Long>> map1 = new HashMap<String,Response<Long>>(20);
         pl.set("shiqing1", "tiancai");
         pl.sadd("shi", "frefbrier");
         pl.get("shiqing1");
         pl.lpush("shiqing", "name");
         pl.hsetnx("qing", "name", "sq");


         //先把要执行的命令都放进管道对象中，下一步就是执行这些命令、一次性执行所有命令
         //在执行execute之前其实命令已经提交、提交之后没有等待响应。
         // 继续执行下一个命令(类似异步请求)。excute只是获取执行结果
        List<Object> list =  pl.execute();
         for(Object  obj:list){
             System.err.println(obj);
         }
         System.err.println(JSONObject.toJSON(list));
     }


    @Test
    public   void  test(){
        RedissonClient redisson = Redisson.create();
        RBatch batch = redisson.createBatch();
        //获取到RBatch对象之后，跟jedis一样，要先把要执行的命令都放进Rbatch。
        /*batch.getSet("setkey").readAllAsync();
        batch.getList("listkey").readAllAsync();
        batch.getMap("hashkey").addAndGetAsync("hashfiled", 2);*/
        batch.getAtomicDouble("key1").incrementAndGetAsync();
        batch.getAtomicLong("key2").incrementAndGetAsync();
        batch.getBucket("name").setAsync("xieqiang");
        batch.getBucket("age").setAsync("36");
        batch.getBitSet("key3");

        //2、声明接收对象  Redisson的管道返回对象是一个任意类型的list
        List<?> res = new ArrayList<>();


        //3、一次性执行所有命令【】
        res = batch.execute();
        for(Object  obj:res){
            System.err.println(obj);
        }
        //Redisson的批量操作是异步的。

        //然后就可以拿到对应的结果。res里面包括全部的命令执行结果。

    }
}
