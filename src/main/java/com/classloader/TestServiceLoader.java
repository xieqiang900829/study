package com.classloader;

import java.lang.String;
import java.util.ServiceLoader;

/**
 * Created by WD42700 on 2019/1/12.
 */
public class TestServiceLoader {

    public static void main(String[] args) throws Exception {

        /**
         *
         * SPI依赖的是接口、而不是类的实现。这样当需要改动的时候 、只要改下配置文件里面的实现类就好
         * ServiceLoader 默认加载的是META-INF.services 目录底下的文件，文件名是接口名、文件内容是实现类，
         * 当需求变更的时候只要、替换jar就可以了 【META-INF.services  也要打包到jar里面？？？？？？】
         *
         * SPI   简单来说就是通过配置文件指定接口的实现类。
         * 让我们可以对接口的实现进行动态替换, 这就是SPI机制. SPI可以降低依赖。尤其在Android项目模块化中极为有用。
         * 比如美团和猫眼的项目中都用到了ServiceLoader。可以实现模块之间不会基于具体实现类硬编码，可插拔
         *
         *ServiceLoader是实现SPI一个重要的类。是jdk6里面引进的一个特性。
         * 在资源目录META-INF/services中放置提供者配置文件，然后在app运行时，遇到Serviceloader.load(XxxInterface.class)时，
         * 会到META-INF/services的配置文件中寻找这个接口对应的实现类全路径名，然后使用反射去生成一个无参的实例(反射也可以实现有参数的实例)。
         *
         * 主要的使用场景是和第三方库解耦，解依赖。比如模块化的时候。比如接触第三方库依赖的时候 比如接触第三方库依赖的时候,例如mysql
         * 一个接口是一个ServiceLoader、
         *
         *
         */

        /*ServiceLoader<Compute>  sl = ServiceLoader.load(Compute.class);
        for (Compute service : sl) {
            System.out.println(service.getClass());
            Integer i = service.add(123);
            System.out.println(i);
            //
        }*/

        ClassLoader  loader1 = ClassLoader.getSystemClassLoader();
        Class c1 = loader1.loadClass("com.classloader.ClassLoaderTree");

        MyClassLoader2 loader2 =new MyClassLoader2();


        System.out.println(MyClassLoader2.class.getClassLoader());

        MyClassLoader2 ld2 = (MyClassLoader2) loader2.loadClass("com.classloader.ClassLoaderTree").newInstance();

      /*  MyClassLoader2 loader3 =new MyClassLoader2();
        Class c3 = loader2.loadClass("com.classloader.ClassLoaderTree");


        System.out.println(loader1 == loader2);
        System.out.println(c3 == c2);*/
    }

}
