package com.javaagent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Created by WD42700 on 2019/4/12.
 */
public class PreMainTraceAgent {

    public static final String injectedClassName = "com.javaagent.Demo";
    public static final String methodName = "print";

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("PreMainTraceAgent____________agentArgs : " + agentArgs);
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                    throws IllegalClassFormatException {
                className = className.replace("/", ".");
                System.out.println(className);
                if (className.equals(injectedClassName)) {
                    System.err.println("99999999999999999999999999999999999999999999999999999999999999999999");
                    try {
                        CtClass ctclass = ClassPool.getDefault().get(className);// 使用全称,用于取得字节码类<使用javassist>
                        CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);// 得到这方法实例
                        ctmethod.insertBefore("System.out.println(11111111111111111111);");
                        return ctclass.toBytecode();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return classfileBuffer;
            }
        }, true);
    }
}
