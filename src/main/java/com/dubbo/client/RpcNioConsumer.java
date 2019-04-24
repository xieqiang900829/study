package com.dubbo.client;

import com.dubbo.server.HelloService;

/**
 * Created by WD42700 on 2019/4/24.
 */
public class RpcNioConsumer {
    public static void main(String[] args) {
        multipartRpcNio();
    }

    /**
     * 多线程IO调用示例
     *
     * @param
     * @return void
     * @createTime：2018/7/1
     * @author: xieqiang
     */
    public static void multipartRpcNio() {
        HelloService proxy = RpcProxyFactory.getMultService(HelloService.class);
        for (int i = 0; i < 100; i++) {
            final int j = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    String result = proxy.sayHello("world!");
                }
            };
            Thread t = new Thread(runnable);
            t.start();
        }
    }
}
