package com.joke.consumer;

import com.joke.annotation.JokeInject;
import com.joke.annotation.JokeService;
import com.joke.provider.HelloService;

@JokeService(HelloCustomer.class)
public class HelloCustomer {

    @JokeInject("helloService")
    private HelloService helloService;

    public String sayHi(String name) {
        return helloService.sayHi(name);
    }
}
