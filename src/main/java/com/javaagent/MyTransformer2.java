package com.javaagent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyTransformer2 implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        className = className.replace("/", ".");
        try{
            if ("com.agent.BeforeDemo".equals(className)){
                CtClass ctclass = ClassPool.getDefault().get(className);
                CtMethod ctMethod = ctclass.getDeclaredMethod("before");
                ctMethod.insertBefore("System.out.println(\"开始处理\");");
                ctMethod.insertAfter("System.out.println(\"结束处理\");");
                return ctclass.toBytecode();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return classfileBuffer;
    }
}
