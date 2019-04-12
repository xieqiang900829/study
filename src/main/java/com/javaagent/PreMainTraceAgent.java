package com.javaagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Created by WD42700 on 2019/4/12.
 */
public class PreMainTraceAgent {

    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中
     * 并被同一个System ClassLoader装载
     * 被统一的安全策略(security policy)和上下文(context)管理
     */
    public static void premain(String agentArgs, Instrumentation inst) {

        System.out.println("PreMainTraceAgent____________agentArgs : " + agentArgs);
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                    throws IllegalClassFormatException {

               if(className.equals("com.classloader.MyClassLoader")){
                    System.out.println("my  name is MyClassLoader:" + className);
                }

                //每个类的加载都会调用这个方法 、可以在这里做字节码增强处理、判断
                System.out.println("类加载器loader     :" + loader.toString());
                System.out.println("premain load Class :" + className);
                return classfileBuffer;//直接将字节码原样返回 不做任何修改。。
            }
        }, true);

    }

}
