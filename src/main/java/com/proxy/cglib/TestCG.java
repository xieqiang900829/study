package com.proxy.cglib;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.FixedValue;

public class TestCG {

    @Test
    public void testFixedValue() throws Exception {

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(CGlibSample.class);

        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "Hello cglib!";
            }
        });
        CGlibSample proxy = (CGlibSample)enhancer.create();
        /*Assert.assertEquals("Hello cglib!",proxy.test("hello!"));
        Assert.assertEquals("Hello cglib!",proxy.toString());
        Assert.assertFalse("hello!".equals
                (proxy.test("hello!")));*/

        proxy.test("1234567890");

    }

}
