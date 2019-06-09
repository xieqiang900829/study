package com.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


import java.lang.reflect.Method;

public class CglibProxyDemo implements MethodInterceptor {
    private Trancation trancation;
    private HelloImpl target;

    public CglibProxyDemo(Trancation trancation, HelloImpl target) {
        this.trancation = trancation;
        this.target = target;
    }
    // 创建代理
    public Object createProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //HelloImpl$$EnhancerByCGLIB$$1dd4a564 extends HelloImpl implements Factory
        System.err.println(o.getClass());//这个是动态生成的代理类，不是目标类。所以不能method.invoke()

        // 开启事务
        trancation.beginTrancation();
        // 方法调用，注！！！此处传入的Object为代理，而不是目标类，目标类需要另外注入
        Object obj = method.invoke(target, objects);//或者methodProxy.invokeSuper(o, objects);
        // 提交事务
        trancation.commit();
        return obj;
    }
}

interface Ihello{
    public void sayHello();
    public void write(String  name);
}

class HelloImpl implements Ihello{
    @Override
    public void sayHello() {
        System.out.println("hello  world");
    }

    @Override
    public void write(String name) {
        System.out.println("write~~~"+name);
    }
}
// 事务
class Trancation {
    public void beginTrancation() {
        System.out.println("开启事务");
    }
    public void commit() {
        System.out.println("提交事务");
    }
}


// 客户端
class Client {
    public static void main(String[] args) {
        // 生成class类的路径
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E:\\weidai_project\\study");
        Trancation t = new Trancation();
        HelloImpl p = new HelloImpl();
        CglibProxyDemo cglibProxy = new CglibProxyDemo(t,p);
        HelloImpl helloImpl = (HelloImpl) cglibProxy.createProxy();
        /*helloImpl.sayHello();
        helloImpl.write("谢强");*/
    }
}

