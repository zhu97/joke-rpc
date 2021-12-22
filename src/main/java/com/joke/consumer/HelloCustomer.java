package com.joke.consumer;

import com.joke.annotation.JokeConsumer;
import com.joke.annotation.JokeInject;
import com.joke.provider.HelloService;

@JokeConsumer
public class HelloCustomer {

    @JokeInject("helloService")
    private HelloService helloService;

    public String sayHi(String name) {
        return helloService.sayHi(name);
    }
}
