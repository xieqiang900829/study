package com.consistenthash;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by WD42700 on 2019/6/6.
 */
public class ConsistentHash {

    public static String[] servers={"12.12.3.45:2081","12.12.3.45:2082","12.12.3.45:2083","12.12.3.45:2084"};
    public static String[] rate={"0.12","0.38","0.26","0.24"};
    public static SortedMap<Long,String > consistentBuckets = new TreeMap<>();
    static {
        init();
    }
    public static void init (){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            int totalWeight = 40 * servers.length;
            for (int i = 0; i < servers.length; i++) {
                String ip = servers[i];
                String weight = rate[i];
                double reactor = Double.valueOf(weight) * totalWeight;
                for (int j = 1; j < reactor; j++) {
                    String key = ip + "_" + j;
                    byte[] d = digest.digest(key.getBytes());
                    // 每个hash值生成4个虚拟节点
                    for (int h = 0; h < 4; h++) {
                        Long k =
                                ((long) (d[3 + h * 4] & 0xFF) << 24)
                                        | ((long) (d[2 + h * 4] & 0xFF) << 16)
                                        | ((long) (d[1 + h * 4] & 0xFF) << 8)
                                        | ((long) (d[0 + h * 4] & 0xFF));

                        // 在环上保存节点
                        consistentBuckets.put(k, servers[i]);
                    }
                }
            }
            System.err.println("哈希环总长度："+consistentBuckets.size());
            for (Map.Entry<Long, String> entry : consistentBuckets.entrySet()) {//
                System.err.println(entry.getValue() + "    hash:" + entry.getKey());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String query(String key) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] bKey = digest.digest(key.getBytes());
        long hv = ((long)(bKey[3]&0xFF) << 24) | ((long)(bKey[2]&0xFF) << 16) | ((long)(bKey[1]&0xFF) << 8 ) | (long)(bKey[0]&0xFF);
        //consistentBuckets.firstKey()
        SortedMap sortedMap = consistentBuckets.tailMap(hv);
        Long  firstKey = 0L;
        if (sortedMap == null){
            firstKey = consistentBuckets.firstKey();
        }else{
            firstKey = consistentBuckets.tailMap(hv).firstKey();
        }
        String  serverIP = consistentBuckets.get(firstKey);

        return serverIP;
    }

    @Test
    public void f1() throws NoSuchAlgorithmException {
        query("xieqiang");
    }

}
