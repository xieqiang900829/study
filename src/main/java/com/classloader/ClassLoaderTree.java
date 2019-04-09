package com.classloader;

import sun.security.ec.SunEC;

import java.util.List;

/**
 * Created by WD42700 on 2019/3/22.
 */
public class ClassLoaderTree {

    public static void main(String[] args) {
        ClassLoader loader = ClassLoaderTree.class.getClassLoader();
        while (loader != null) {
            System.out.println(loader.toString());
            loader = loader.getParent();
        }

        SunEC   ec   =new  SunEC() ;
        loader = SunEC.class.getClassLoader();
        System.out.println("path = " + loader);//Ext Loader
        /*String path = List.class.getResource("/").toString();
        System.out.println("path = " + path);*/


    }

}
