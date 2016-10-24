package com.example;

/**
 * @author hiphonezhu@gmail.com
 * @version [JavaProxy, 16/10/12 09:44]
 */

public class HelloImpl implements IHello {
    @Override
    public void sayHello() {
        System.out.println("say hello");
    }
}
