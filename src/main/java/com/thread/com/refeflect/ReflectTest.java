package com.thread.com.refeflect;


import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.String;
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

    @Test
    public void  test5(){
        final  Student s = new Student();
        s.setAge(89);
        System.out.println(JSONObject.toJSONString(s));

       // s = new Student();//会报错、final 变量不能直接被修改  ，但是可以通过 set 、get方法等修改


    }


    @Test
    public void  test6() throws Exception {
        //一些私有方法、私有的方法如何修改，常规的方法调用肯定不行、通过类的反射机制、修改访问权限
        String s = "Hello World";
        System.out.println("s = " + s); //Hello World

        //获取String类中的value字段
        Field valueFieldOfString = String.class.getDeclaredField("value");

        //改变value属性的访问权限
        valueFieldOfString.setAccessible(true);

        //获取s对象上的value属性的值
        char[] value = (char[]) valueFieldOfString.get(s);

        //改变value所引用的数组中的第5个字符
        value[5] = '_';
        System.out.println("s = " + s);      //Hello_World
    }


    @Test
    public void  test7() throws Exception {

      /*  byte  a = 1;
        byte  b = 3;
        //byte  c = a+b; 		//直接加会出错，因为运算时Java虚拟机进行了转换，导致把一个int赋值给byte

        final  byte  d = 1;
        final  byte  e = 3;
        byte f = d + e;		//加上final就不会出错。*/


        /*char a= 'g';
        int c = a;
        System.out.println("c = " + c +"   a="+a);//c = 103   a=g*/

        byte a=-128;//byte范围是 —128 到 +127  byte占一个字节 、8位，第一位代表的是正负
        byte b=127;


        int  c=20;
        int  d =+c;
        int  e =-c;
        System.out.println("c = " + c +"   d="+d+"  e="+e);


        double  f =1.2;
        f=-f;


    }

}
