package com.joke.provider.impl;

import com.joke.annotation.JokeService;
import com.joke.provider.HelloService;

@JokeService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHi(String msg) {
        return "provider say " + msg;
    }
}
