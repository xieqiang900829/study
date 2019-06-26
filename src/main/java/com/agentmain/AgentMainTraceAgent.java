package com.agentmain;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

public class AgentMainTraceAgent {

    public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        System.out.println("Agent Main called,agentArgs :" + agentArgs);
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                    throws IllegalClassFormatException {
                System.out.println("agentmain load Class  :" + className);
                return classfileBuffer;
            }
        }, true);
        inst.retransformClasses(Account.class);//指定需要重新转换的类
    }


}
