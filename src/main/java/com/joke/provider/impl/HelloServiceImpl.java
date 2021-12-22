package com.joke.provider.impl;

import com.joke.annotation.JokeProvider;
import com.joke.provider.HelloService;

@JokeProvider(value = "helloService")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHi(String msg) {
        return "provider say " + msg;
    }
}
