package com.javaagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Created by WD42700 on 2019/4/12.
 */
public class PreMainTraceAgent {

    public static void premain(String agentArgs, Instrumentation inst) {

        System.out.println("PreMainTraceAgent____________agentArgs : " + agentArgs);
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                    throws IllegalClassFormatException {
                System.out.println("premain load Class     :" + className);
                return classfileBuffer;
            }
        }, true);

        //java  -javaagent:G:\myagent.jar=Hello2 -javaagent:G:\myagent.jar=Hello3 -jar myapp.jar
        //java -javaagent:study-1.0-SNAPSHOT.jar=Hello1 -javaagent:study-1.0-SNAPSHOT.jar=Hello2 -jar myapp.jar
        //java  -javaagent:study-1.0-SNAPSHOT.jar=Hello1 -jar  study-1.0-SNAPSHOT.jar
    }

}
