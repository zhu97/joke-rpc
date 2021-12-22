package com.joke.simples.caller;

import com.joke.simples.handler.DynamicProxyHandler;
import com.joke.simples.service.HelloService;

import java.lang.reflect.Proxy;

public class Caller {
    public static void main(String args[]) {
        HelloService echo = (HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(),
                new Class<?>[]{HelloService.class}, new DynamicProxyHandler());

        for (int i = 0; i < 3; i++) {
            System.out.println(echo.sayHi(String.valueOf(i)));
        }
    }
}
