package com.joke.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * rpc服务启动配置
 */
@ConfigurationProperties(prefix = "joke.rpc")
@Data
public class JokeRpcProperties extends Properties {

    private String host = "127.0.0.1";

    private int port = 9090;
}
