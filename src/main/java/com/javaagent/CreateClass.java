package com.javaagent;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AllPermission;
import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMember;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * Created by WD42700 on 2019/4/15.
 */
public class CreateClass {

    public static void text0() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("study.Person");

        // 创建属性
        CtField f = CtField.make("private int id;", cc);
        CtField f2 = CtField.make("private String name;", cc);
        cc.addField(f);
        cc.addField(f2);

        // 创建方法
        CtMethod m = CtMethod.make("public int getId(){return this.id;}", cc);
        CtMethod m2 = CtMethod.make("public void setId(int id){this.id = id;}", cc);
        cc.addMethod(m);
        cc.addMethod(m2);

        // 添加构造器
        CtConstructor c = new CtConstructor(new CtClass[] { CtClass.intType, pool.get("java.lang.String") }, cc);
        // 构造方法体
        c.setBody("{this.id = id;this.name = name;}");
        cc.addConstructor(c);
        // 放入工作空间
        cc.writeFile("D:/text");
        System.out.println("生成的类用反编译工具查看");
    }

    /**
     * 处理类的基本用法
     *
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     */
    public static void text1() throws NotFoundException, IOException, CannotCompileException {
        // 获得池对象
        ClassPool pool = ClassPool.getDefault();
        // 获得指定某个类
        CtClass cc = pool.get("study.Person");
        byte[] bytes = cc.toBytecode();
        System.out.println(Arrays.toString(bytes));

        System.out.println(cc.getName());// 获得类名
        System.out.println(cc.getSimpleName());
        System.out.println(cc.getSuperclass());// 获得父类
    }

    /**
     * 测试产生新的方法
     *
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     */
    public static void text2()
            throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException,
            NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        // 获得池对象
        ClassPool pool = ClassPool.getDefault();
        // 获得指定某个类
        CtClass cc = pool.get("study.Person");

        // CtMember m = CtNewMethod.make("public int add(int a,int b){return
        // a+b}", cc);
        // 参数 指定返回类型 方法的名称 方法的参数
        CtMember m = new CtMethod(CtClass.intType, "add", new CtClass[] { CtClass.intType, CtClass.intType }, cc);
        // 是什么方法
        m.setModifiers(Modifier.PUBLIC);
        ((CtBehavior) m).setBody("{return $1+$2;}");// $0 为this $后为形参
        cc.addMethod((CtMethod) m);
        // 通过反射调用新生成的方法
        Class clazz = cc.toClass();
        Object obj = clazz.newInstance();// 通过调用person无参构造，创建新的person对象
        Method method = clazz.getDeclaredMethod("add", int.class, int.class);
        System.out.println(method.invoke(obj, 200, 300));
    }

    /**
     * 修改方法已经有的信息
     *
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static void text3()
            throws NotFoundException, CannotCompileException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // 获得池对象
        ClassPool pool = ClassPool.getDefault();
        // 获得指定某个类
        CtClass cc = pool.get("study.Person");

        CtMethod cm = cc.getDeclaredMethod("say", new CtClass[] { CtClass.intType });// 第二个参数为数组
        // 修改方法 在方法前加 在方法的第几行加 在方法后面加
        cm.insertBefore("System.out.println($1+\" start\");");
        cm.insertAt(8, "int b = 3;System.out.println(b);");
        cm.insertAfter("System.out.println($1+\" end\");");

        // 通过反射调用新生成的方法
        Class clazz = cc.toClass();
        Object obj = clazz.newInstance();
        Method m = clazz.getDeclaredMethod("say", int.class);
        System.out.println(m.invoke(obj, 12));
    }

    /**
     * 属性的操作
     *
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    public static void text4() throws NotFoundException, CannotCompileException {
        // 获得池对象
        ClassPool pool = ClassPool.getDefault();
        // 获得指定某个类
        CtClass cc = pool.get("study.Person");

        // CtField field = CtField.make("private int id;", cc);
        CtField f1 = new CtField(CtClass.intType, "age", cc);
        f1.setModifiers(Modifier.PRIVATE);
        // 加上值
        cc.addField(f1, "10");

        // 获取值
        // cc.getDeclaredField("name");
        // 增加get set 方法
        cc.addMethod(CtNewMethod.getter("getAge", f1));
        cc.addMethod(CtNewMethod.getter("setAge", f1));

    }

    /**
     * 构造方法的操作
     *
     * @throws NotFoundException
     */
    public static void text5() throws NotFoundException {
        // 获得池对象
        ClassPool pool = ClassPool.getDefault();
        // 获得指定某个类
        CtClass cc = pool.get("study.Person");

        CtConstructor[] cs = cc.getConstructors();
        for (CtConstructor c : cs) {
            System.out.println(c.getLongName());
            //c.insertBefore("");添加方法
        }
    }
  /*  public static void text6() throws NotFoundException, ClassNotFoundException {
        CtClass cc = ClassPool.getDefault().get("study.Person");
        Object[] all = cc.getAnnotations();
        Author a = (Author) all[0];
        String name = a.name();
        int year = a.year();
        System.out.println("name "+name + " year "+ year);
        //能进一步操作
    }*/

    public static void main(String[] args) throws Exception {
        text0();
        text1();
        // text2();
        // text3();
        // text4();
        // text5();
        // text6();
    }


}
