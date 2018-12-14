package com.thread.com.refeflect;


import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WD42700 on 2018/12/11.
 */
public class ReflectTest {

    /**
     * Class类型的对象，也就是字节码文件对象。获取字节码文件对象的三种方式
     *1、Class clazz1 = Class.forName("全限定类名");　　//通过Class类中的静态方法forName，直接获取到一个类的字节码文件对象，此时该类还是源文件阶段，并没有变为字节码文件。
     *2、Class clazz2  = Person.class;　　　　//当类被加载成.class文件时，此时Person类变成了.class，在获取该字节码文件对象，也就是获取自己， 该类处于字节码阶段。
     *3、 Class clazz3 = p.getClass();　　　　//通过类的实例获取该类的字节码文件对象，该类处于创建对象阶段　 o=new  Person();
     *
     */


    @Test
    public  void test1() throws Exception {
        Class  utilClass = Class.forName("com.thread.com.refeflect.Util");
        Object utilInStance = utilClass.newInstance();//调用无参的构造函数

        Method method = utilClass.getMethod("add", Integer.class,Integer.class);

        Integer  a = 10;
        Integer  b = 20;

       //错误用法  Integer result = (Integer) e.invoke(a,b);  有问题
       //正确用法  Integer result = (Integer) e.invoke(utilInStance,a,b);

        Integer result = (Integer) method.invoke(utilInStance,a,b);

        System.out.println(result);

    }

    @Test
    public void test2() throws Exception {
        Class  studentClass = Class.forName("com.thread.com.refeflect.Student");
        Method[] methodList = studentClass.getMethods();

        for(Method method:methodList){
            System.out.println(method);
        }

        System.out.println("\n getFields\n");//获取public 字段
        Field[] fields = studentClass.getFields();
        for (Field field:fields){
            System.out.println(field);
        }

        System.out.println("\n getDeclaredFields\n");  //获取全部的字段
        Field[] declaredFields = studentClass.getDeclaredFields();
        for (Field field:declaredFields){
            System.out.println(field);
        }

    }


    @Test
    public void test3() throws Exception {
        Class  studentClass = Class.forName("com.thread.com.refeflect.Student");
        Constructor constructor =  studentClass.getConstructor(Integer.class,String.class);

        Student student = (Student) constructor.newInstance(500,"xieqiang");

        System.out.println(student);

    }


    @Test
    public void test4() throws Exception {
        //利用反射，在泛型为int的arryaList集合中存放一个String类型的对象

        /*List<Object> list =new ArrayList<>();
        list.add(12);
        list.add(34);
        list.add("dsfdsf");
        System.out.println(list);*/

        List<Integer> list =new ArrayList<>();
        list.add(12);
        list.add(34);

        Class  listClass = list.getClass();

        Method  addMethod = listClass.getMethod("add",Object.class);

        addMethod.invoke(list,"abcd");
        System.out.println(list);

    }


}
