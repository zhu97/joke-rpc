package com.joke.config;

import com.alibaba.boot.nacos.config.properties.NacosConfigProperties;
import com.joke.properties.JokeRpcProperties;
import com.joke.register.Register;
import com.joke.register.nacos.NacosRegister;
import com.joke.starter.RpcStarter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcConfig {


    @Bean
    public Register register(NacosConfigProperties nacosConfigProperties, JokeRpcProperties jokeRpcProperties) {
        return new NacosRegister(nacosConfigProperties, jokeRpcProperties);
    }

    @Bean
    public RpcStarter rpcStarter(Register register, JokeRpcProperties jokeRpcProperties) {
        return new RpcStarter(register, jokeRpcProperties);
    }
}
