package com.classloader;

import com.thread.com.refeflect.Student;

/**
 * Created by WD42700 on 2019/4/15.
 */
public class MyClassLoader2 extends ClassLoader  {


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    public Class<?> defineMyClass( byte[] b, int off, int len)
    {
        return super.defineClass(b, off, len);
    }


    public static void main(String[] args) throws Exception {
        MyClassLoader2 loader1 = new MyClassLoader2();
        MyClassLoader2 loader2 = new MyClassLoader2();

        Class<Student> student1 = (Class<Student>) loader1.loadClass("com.thread.com.refeflect.Student");

        Class<Student> student2 = (Class<Student>) loader2.loadClass("com.thread.com.refeflect.Student");

        loader1.getClass().getClassLoader();
        MyClassLoader2.class.getClassLoader();
        Thread.currentThread().getContextClassLoader();
        System.out.print(ClassLoader.getSystemClassLoader());

    }
}
