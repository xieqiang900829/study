package com.javaagent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Created by WD42700 on 2019/4/12.
 */
public class PreMainTraceAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        MyTransformer transformer = new MyTransformer();
        inst.addTransformer(transformer,true);
    }
}
