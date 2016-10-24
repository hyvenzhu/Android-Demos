package com.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyClass {
    public static void main(String[] args)
    {
        final IHello hello = new HelloImpl();
        IHello helloProxy = (IHello)Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("do before");
                Object result = method.invoke(hello, args);
                System.out.println("do after");
                return result;
            }
        });
        helloProxy.sayHello();
    }
}
