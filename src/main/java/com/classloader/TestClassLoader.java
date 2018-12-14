package com.classloader;

import com.thread.com.refeflect.Student;
import org.junit.Test;

/**
 * Created by WD42700 on 2018/12/13.
 */
public class TestClassLoader {

    @Test
    public void  test(){
        Student  student = new  Student();//如果有带参的构造函数。没有显示声明 无参构造器 。会报错

        //BootstrapClassLoader  -> ExtClassLoader  ->AppClassLoader
        //ExtClassLoader 是 AppClassLoader的父类 加载器 AppClassLoader.parent()

       /* //sun.misc.Launcher$AppClassLoader@4e25154f
        ClassLoader  classLoader = student.getClass().getClassLoader();//自己编写的类 都是 AppClassLoader
        System.out.println(classLoader);

        //sun.misc.Launcher$AppClassLoader@4e25154f
        System.out.println(Student.class.getClassLoader());*/

        //Bootstrap Loader是用C++语言写的，依java的观点来看，逻辑上并不存在Bootstrap Loader的类实体，
        // 所以在java程序代码里试图打印出其内容时，我们就会看到输出为null。
        System.out.println(java.util.List.class.getClassLoader()); //jdk自带的类都是由 根加载器加载的 。。
        System.out.println(java.lang.Object.class.getClassLoader());//null

        System.out.println(java.util.List.class.getClassLoader());

    }
}
