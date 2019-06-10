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

    public final String injectedClassName = "com.javaagent.Demo";
    public final String methodName = "print";

    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中
     * 并被同一个System ClassLoader装载
     * 被统一的安全策略(security policy)和上下文(context)管理
     */
    public  void premain(String agentArgs, Instrumentation inst) {

        System.out.println("PreMainTraceAgent____________agentArgs : " + agentArgs);
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                    throws IllegalClassFormatException {
                //每个类的加载都会调用这个法 、可以在这里做字节码增强处理、判断
                //为什么加载的全是com.proxy目录下的类
                System.out.println("premain load Class________    " + className);

                //直接将字节码原样返回 不做任何修改。。如何修改成自己需要的字节码
                //使用javaagent修改，修改完了再将class转成byte[] 返回
                className = className.replace("/", ".");
                System.out.println(className);
                if (className.equals(injectedClassName)) {
                    CtClass ctclass = null;
                    try {
                        ctclass = ClassPool.getDefault().get(className);// 使用全称,用于取得字节码类<使用javassist>
                        CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);// 得到这方法实例
                        ctmethod.insertBefore("System.out.println(11111111);");
                        return ctclass.toBytecode();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }, true);

    }

}
