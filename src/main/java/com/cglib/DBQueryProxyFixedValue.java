package com.cglib;

import net.sf.cglib.proxy.FixedValue;

public class DBQueryProxyFixedValue implements FixedValue {

    public Object loadObject() throws Exception {
        System.out.println("Here in DBQueryProxyFixedValue ! ");
        return "Fixed Value";
    }
}

