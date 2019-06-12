package com.javaagent;

import com.classloader.MyClassLoader;
import com.classloader.MyClassLoader2;
import javassist.*;
import org.junit.Assert;
import org.junit.Test;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class JavaAgentDemo {

    @Test
    public void  f1() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("com.javaagent.Util");

       CtMethod ctMethod = CtMethod.make("public void add(){\n" +
               "        List<String> list =null;\n" +
               "    }",ctClass);
        ctClass.addMethod(ctMethod);

        byte[] bytes = ctClass.toBytecode();

        OutputStream out = new FileOutputStream("E:\\jd_gui\\class_dir\\Util.class");
        out.write(bytes);
    }

    @Test
    public void  f2() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("com.javaagent.Util");
        CtField ctField = CtField.make("private int age;",ctClass);
        ctClass.addField(ctField);

        CtMethod ctMethod = CtMethod.make("public void print(){\n" +
                "        System.out.println(100);\n" +
                "    }",ctClass);
        ctClass.addMethod(ctMethod);

        //输出到磁盘
        byte[] bytes = ctClass.toBytecode();
        OutputStream out = new FileOutputStream("E:\\jd_gui\\class_dir\\Util.class");
        out.write(bytes);

        //方法调用
        Class c = ctClass.toClass();
        Object obj = c.newInstance();
        Method method = c.getMethod("print");
        method.invoke(obj);
    }


    @Test
    public void  f3() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.javaagent.Demo");
        CtMethod ctMethod = ctClass.getDeclaredMethod("add",new CtClass[]{CtClass.intType,CtClass.intType});

        ctMethod.insertBefore("{System.out.println(\"开始执行\");}");
        ctMethod.insertAfter("{System.out.println(\"结束执行\");}",false);
        //方法调用
        Class c = ctClass.toClass();
        //toClass、writeFile 、tobytecode之后CtClass被冻结，该类已经被加载到JVM、不允许再修改
        Object obj = c.newInstance();
        Method method = c.getMethod("add",int.class,int.class);
        method.invoke(obj,200,300);
    }

    @Test
    public void f4() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.javaagent.Demo");
        ctClass.setName("Demo1");

        CtClass ctClass1 = pool.get("com.javaagent.Demo");
        CtClass ctClass2 = pool.get("Demo1");

        Assert.assertNotNull(ctClass1);//true
        Assert.assertNotNull(ctClass2);//true
    }

    @Test
    public void f5() throws Exception {
        //javassist同个Class是不能在同个ClassLoader中加载两次的。所以在输出CtClass的时候需要注意下
        Demo demo = new Demo();
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.javaagent.Demo");
        //ctClass.toClass();//会报错  因为同个Class是不能在同个ClassLoader中加载两次
        ctClass.toClass(new MyClassLoader2());
    }

    @Test
    public void f6() throws Exception {
        System.out.println(String.class.getClassLoader());
    }

    @Test
    public void  f7() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.javaagent.Demo");
        CtMethod ctMethod = ctClass.getDeclaredMethod("add",new CtClass[]{CtClass.intType,CtClass.intType});

        ctMethod.insertBefore(
                " System.out.println($0);" +
                "System.out.println($args);" +
                "System.out.println($class);" +
               // "System.out.println($r);" +  //用于强制类型转换、不能直接输出
               // "System.out.println($w);" +
               // "System.out.println($_);" +
                "System.out.println( $sig);" +
                "System.out.println($1); System.out.println($2);");
        //方法调用
        Class c = ctClass.toClass();
        //toClass、writeFile 、tobytecode之后CtClass被冻结，该类已经被加载到JVM、不允许再修改
        Object obj = c.newInstance();
        Method method = c.getMethod("add",int.class,int.class);
        method.invoke(obj,200,300);
    }

    @Test
    public void addCatch()throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.javaagent.Demo");
        CtMethod method = ctClass.getDeclaredMethod("add",new CtClass[]{CtClass.intType,CtClass.intType});
        CtClass exceptionType = pool.get("java.io.IOException");
       // method.addLocalVariable("myAge",CtClass.intType);  //不生效
        method.addCatch("{ System.out.println($e); throw $e; }",exceptionType);

       ctClass.writeFile("E:\\jd_gui\\class_dir");
    }

    @Test
    public void remove()throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.javaagent.Demo");

        ctClass.removeField(ctClass.getDeclaredField("name"));
        ctClass.removeMethod(ctClass.getDeclaredMethod("print"));

        ctClass.writeFile("E:\\jd_gui\\class_dir");
    }

    @Test
    public void testBeforeAndAfter()throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.javaagent.Demo");
        CtMethod ctMethod = ctClass.getDeclaredMethod("add",new CtClass[]{CtClass.intType,CtClass.intType});

        ctMethod.insertBefore("{System.out.println(\"开始执行\");}");
        ctMethod.insertAfter("{System.out.println(\"结束执行\");}",false);
        ctClass.writeFile("E:\\jd_gui\\class_dir");

        Class c = ctClass.toClass();
        Object obj = c.newInstance();
        Method method = c.getMethod("add",int.class,int.class);
        method.invoke(obj,200,300);
    }

    @Test
    public void f8()throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.javaagent.Demo");
        CtMethod ctMethod = ctClass.getDeclaredMethod("add",new CtClass[]{CtClass.intType,CtClass.intType});

        ctMethod.insertBefore("{System.out.println(\"开始执行\");}");
        ctMethod.insertAfter("{System.out.println(\"结束执行\");}",false);

        byte[] bytes = ctClass.toBytecode();

        MyClassLoader2 loader = new MyClassLoader2();
        Class  newClass = loader.defineMyClass(bytes,0,bytes.length);
        System.err.println(newClass.getName());
        Method method = newClass.getMethod("add",int.class,int.class);
        method.invoke(newClass.newInstance(),200,300);
    }
}
