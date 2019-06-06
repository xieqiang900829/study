package com.cglib;

import com.cglib.lazyloader.Student;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.junit.Test;

import java.lang.reflect.Method;

public class TestCGLibProxy {

    @Test
    public void  test1(){
        DBQueryProxy dbQueryProxy = new DBQueryProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DBQuery.class);
        enhancer.setCallback(dbQueryProxy);
        //DBQuery dbQuery = (DBQuery)enhancer.create(new Class[]{Integer.class}, new Object[]{1});
        DBQuery dbQuery = (DBQuery) enhancer.create();
        System.out.println(dbQuery.getElement("Hello"));
        System.out.println();
        System.out.println(dbQuery.getAllElements());
        System.out.println();
        System.out.println(dbQuery.sayHello());
    }

    @Test
    public void  test2(){
        DBQueryProxy dbQueryProxy = new DBQueryProxy();
        DBQueryProxy2 dbQueryProxy2 = new DBQueryProxy2();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DBQuery.class);
        enhancer.setCallbacks(new Callback[]{dbQueryProxy, dbQueryProxy2});
        enhancer.setCallbackFilter(new CallbackFilter() {
            public int accept(Method method) {
                if (method.getName().equals("getElement")) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        DBQuery dbQuery = (DBQuery) enhancer.create();
        System.out.println("========Inteceptor By DBQueryProxy ========");
        System.out.println(dbQuery.getElement("Hello"));
        System.out.println();
        System.out.println("========Inteceptor By DBQueryProxy2 ========");
        System.out.println(dbQuery.getAllElements());
    }

    @Test
    public void  test3(){
        DBQueryProxy dbQueryProxy = new DBQueryProxy();
        DBQueryProxy2 dbQueryProxy2 = new DBQueryProxy2();
        Callback noopCb = NoOp.INSTANCE;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DBQuery.class);
        enhancer.setCallbacks(new Callback[]{dbQueryProxy, dbQueryProxy2, noopCb});
        enhancer.setCallbackFilter(new CallbackFilter() {
            public int accept(Method method) {
                if (method.getName().equals("getElement")) {
                    return 0;
                } else if (method.getName().equals("getAllElements")) {
                    return 1;
                } else {
                    return 2;
                }
            }
        });
        DBQuery dbQuery = (DBQuery) enhancer.create();
        System.out.println("========Inteceptor By DBQueryProxy ========");
        System.out.println(dbQuery.getElement("Hello"));
        System.out.println();
        System.out.println("========Inteceptor By DBQueryProxy2 ========");
        System.out.println(dbQuery.getAllElements());
        System.out.println();
        System.out.println("========Return Original Value========");
        System.out.println(dbQuery.methodForNoop());
    }

    @Test
    public void  test4(){
        DBQueryProxy dbQueryProxy = new DBQueryProxy();
        DBQueryProxy2 dbQueryProxy2 = new DBQueryProxy2();
        Callback noopCb = NoOp.INSTANCE;
        Callback fixedValue = new DBQueryProxyFixedValue();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DBQuery.class);
        enhancer.setCallbacks(new Callback[]{dbQueryProxy, dbQueryProxy2, noopCb, fixedValue});
        enhancer.setCallbackFilter(new CallbackFilter() {
            public int accept(Method method) {
                if (method.getName().equals("getElement")) {
                    return 0;
                } else if (method.getName().equals("getAllElements")) {
                    return 1;
                } else if (method.getName().equals("methodForNoop")) {
                    return 2;
                } else if (method.getName().equals("methodForFixedValue")) {
                    return 3;
                } else {
                    return 0;
                }
            }
        });
        DBQuery dbQuery = (DBQuery) enhancer.create();
        System.out.println("========Inteceptor By DBQueryProxy ========");
        System.out.println(dbQuery.getElement("Hello"));
        System.out.println();
        System.out.println("========Inteceptor By DBQueryProxy2 ========");
        System.out.println(dbQuery.getAllElements());
        System.out.println();
        System.out.println("========Return Original Value========");
        System.out.println(dbQuery.methodForNoop());
        System.out.println();
        System.out.println("========Return Fixed Value========");
        System.out.println(dbQuery.methodForFixedValue("myvalue"));
    }

    @Test
    public void  test5(){
        Student student = new Student(666, "XiaoMing");
        System.out.println("id=" + student.getId());
        System.out.println("name=" + student.getName());
        // LazyLoader 只有第一次，Dispatcher是每次都会进loadObject的方法
        System.out.println("========First Get  EnglishSchedule ========");
        System.out.println(student.getEnglishSchedule());
        System.out.println();
        System.out.println("========First Get  MathSchedule ========");
        System.out.println(student.getMathSchedule());
        System.out.println();
        System.out.println("========Second Get  EnglishSchedule ========");
        System.out.println(student.getEnglishSchedule());
        System.out.println();
        System.out.println("========Second Get  MathSchedule ========");
        System.out.println(student.getMathSchedule());
    }

    @Test
    public void  test6(){

    }

    @Test
    public void  test7(){

    }

    @Test
    public void  test8(){

    }

    @Test
    public void  test9(){

    }


}
