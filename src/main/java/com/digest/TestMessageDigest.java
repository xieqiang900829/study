package com.digest;

import org.junit.Test;
import sun.security.provider.MD5;

import java.security.MessageDigest;
import java.util.TreeMap;

/**
 * Created by WD42700 on 2019/6/6.
 */
public class TestMessageDigest {

    @Test
    public void  f1() throws Exception {
        MessageDigest digest =  MessageDigest.getInstance("MD5");

        String[] str ={"1","343","rtretrtre","xieqiang","dddddddddddddddddddddddddddddddd","12345"};
        for (String s:str){
            digest.reset();
            digest.update(String.valueOf(s).getBytes());
            byte[] bKey = digest.digest();
            System.err.println(bKey.length);
        }
    }


    @Test
    public void  f2() throws Exception {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update("xieqiang".getBytes());
        byte[] arr = digest.digest();
        System.err.println(arr);
        print(arr);
    }

    @Test
    public void  f3() throws Exception {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] arr = "xieqiang".getBytes();
        byte[] result1 = digest.digest(arr);
        print(result1);

        System.err.println("\n~~~~~~~~~~~~~~~~~~~~\n");
        digest.reset();
        digest.update("xieqiang".getBytes());
        byte[] result2 =new byte[16] ;
        digest.digest(result2,0,16);

        print(result2);
    }

    @Test
    public void  f4() throws Exception {
        byte a = -127;
        int b = a & 0xff;
        System.out.println(b);

        byte c = 127;
        int d = c & 0xff;
        System.out.println(d);
    }

    @Test
    public void f5()throws Exception{
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] arr = "xieqiang".getBytes();
        byte[] bKey = digest.digest(arr);

        long res = ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16)
                | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF);
        System.out.println(res);
        //816006752
        //2485488733
        //1050648900

    }

    public void print(byte[] arr){
        System.err.print("长度:"+arr.length+" ");
        for (byte b:arr){
            System.err.print(b+" ");
        }
    }

   /* TreeMap consistentBuckets;
    Long totalWeight =0;

    public void  f6(){
       *//* this.consistentBuckets = new TreeMap();

        MessageDigest md5 = MD5.get();//用MD5来计算key和server的hash值

// 计算总权重
        if ( this.totalWeight != null)
            for ( int i = 0; i < this.weights.length; i++ )
            this.totalWeight += ( this.weights[i] == null ) ? 1 : this.weights[i];
        } else if (this.weights == null) {
            this.totalWeight = this.servers.length;
        }

    (int i = 0; i < servers.length; i++ ) {// 为每个server分配虚拟节点
        for (
        // 计算当前server的权重
        int thisWeight = 1;
        if ( this.weights != null && this.weights[i] != null )
            thisWeight = this.weights[i];

        // factor用来控制每个server分配的虚拟节点数量
        // 权重都相同时，factor=40
        // 权重不同时，factor=40*server总数*该server权重所占的百分比
        // 总的来说，权重越大，factor越大，可以分配越多的虚拟节点
        double factor = Math.floor( ((double)(40 * this.servers.length * thisWeight)) / (double)this.totalWeight );

        for ( long j = 0; j < factor; j++ ) {
            // 每个server有factor个hash值
            // 使用server的域名或IP加上编号来计算hash值
            // 比如server - "172.45.155.25:11111"就有factor个数据用来生成hash值：
            // 172.45.155.25:11111-1, 172.45.155.25:11111-2, ..., 172.45.155.25:11111-factor
            byte[] d = md5.digest( ( servers[i] + "-" + j ).getBytes() );

            // 每个hash值生成4个虚拟节点
            for ( int h = 0 ; h < 4; h++ ) {
                Long k =
                        ((long)(d[3+h*4]&0xFF) << 24)
                                | ((long)(d[2+h*4]&0xFF) << 16)
                                | ((long)(d[1+h*4]&0xFF) << 8 )
                                | ((long)(d[0+h*4]&0xFF));

                // 在环上保存节点
                consistentBuckets.put( k, servers[i] );
            }

        }
        // 每个server一共分配4*factor个虚拟节点*//*

    }*/



}
