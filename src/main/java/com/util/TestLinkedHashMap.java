package com.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by WD42700 on 2019/7/11.
 */
public class TestLinkedHashMap {

    //顺序插入 、顺序访问
    public static void main(String[] args) {
        orderInsert();
        orderAccess();
    }


    //顺序插入
    public static void  orderInsert(){
        Map<String, Integer> seqMap = new LinkedHashMap<>();
        seqMap.put("c",100);
        seqMap.put("d",200);
        seqMap.put("a",500);
        seqMap.put("d",300);
        for(Map.Entry<String,Integer> entry:seqMap.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
    }


    //顺序访问
    public static void orderAccess(){
        System.out.println("\n\n");
        Map<String, Integer> accessMap = new LinkedHashMap<>(16,0.75f,true);
        accessMap.put("c",100);
        accessMap.put("d",200);
        accessMap.put("a",500);
        accessMap.get("c");
        accessMap.put("d",300);
        for(Map.Entry<String,Integer> entry:accessMap.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
    }


}
