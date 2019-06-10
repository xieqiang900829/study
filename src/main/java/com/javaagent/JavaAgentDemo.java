package com.javaagent;

import javassist.*;
import org.junit.Test;


import java.io.FileOutputStream;
import java.io.OutputStream;

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

        byte[] bytes = ctClass.toBytecode();
        OutputStream out = new FileOutputStream("E:\\jd_gui\\class_dir\\Util.class");
        out.write(bytes);
    }
}
