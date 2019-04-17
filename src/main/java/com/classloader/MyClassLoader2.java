package com.classloader;

/**
 * Created by WD42700 on 2019/4/15.
 */
public class MyClassLoader2 extends ClassLoader  {


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }
}
