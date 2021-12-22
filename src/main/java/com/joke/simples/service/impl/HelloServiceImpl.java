package com.joke.simples.service.impl;

import com.joke.simples.service.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        return "hi joke " + name;
    }
}
