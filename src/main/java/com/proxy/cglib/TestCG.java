package com.proxy.cglib;

import net.sf.cglib.proxy.*;
import org.junit.Assert;
import org.junit.Test;


import java.lang.reflect.Method;

public class TestCG {

    @Test
    public void testFixedValue() throws Exception {

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(CGlibSample.class);//

        //这里的FixedValue接口相当于一个拦截器(interceptor)、由以上代码可得cglib可以改变方法、 创建代理对象的目标对象(这里是CGlibSample)一定要有无参构造方法
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "Hello cglib!";
            }
        });
        CGlibSample proxy = (CGlibSample)enhancer.create();
        Assert.assertEquals("Hello cglib!",proxy.test("hello!"));
        Assert.assertEquals("Hello cglib!",proxy.toString());
        Assert.assertFalse("hello!".equals
                (proxy.test("hello!")));

        String   s = proxy.test("1234567890");
        System.err.println("s = "+s);
    }

    @Test
    public void testInvocationHandler(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGlibSample.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                /**
                 * method.getDeclaringClass() != Object.class  表示该方法不能是Object类中定义的方法，如：toString()。
                 * method.getReturnType() == String.class  表示方法的返回类型只能为String类型
                 */
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return "Hello cglib!";
                } else {
                   // method.invoke(o,objects);//栈内存溢出、死循环
                    return "do not know what to do";
                }
            }
        });

        CGlibSample proxy = (CGlibSample) enhancer.create();
        Assert.assertTrue("Hello cglib!".equals(proxy.test("hello!")));
        Assert.assertTrue("do not know what to do".equals(proxy.toString()));
    }

    @Test
    public void testMethodInterceptor(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGlibSample.class);
        enhancer.setCallback(new MethodInterceptor(){
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return "Hello cglib!";
                } else {
                    //这里有点像struts2中拦截器链
                    return proxy.invokeSuper(obj,args);
                }
            }
        });
        CGlibSample proxy = (CGlibSample) enhancer.create();
        Assert.assertTrue("Hello cglib!".equals(proxy.test("hello!")));
        Assert.assertFalse("do not know what to do".equals(proxy.toString()));
    }

}
