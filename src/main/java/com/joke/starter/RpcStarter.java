package com.joke.starter;

import com.joke.annotation.JokeService;
import com.joke.netty.NettyServer;
import com.joke.properties.JokeRpcProperties;
import com.joke.register.Register;
import com.joke.util.ServiceUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.HashMap;
import java.util.Map;

public class RpcStarter implements ApplicationListener<ContextRefreshedEvent> {

    private final Register register;

    private final JokeRpcProperties jokeRpcProperties;

    public RpcStarter(Register register, JokeRpcProperties jokeRpcProperties) {
        this.register = register;
        this.jokeRpcProperties = jokeRpcProperties;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        registerService(applicationContext);
        subscribeService(applicationContext);
        //netty start
        new Thread(() -> NettyServer.start(jokeRpcProperties)).start();
    }

    private void subscribeService(ApplicationContext applicationContext) {

    }

    private void registerService(ApplicationContext applicationContext) {
        Map<String, Object> beansWithAnnotation =
                applicationContext.getBeansWithAnnotation(JokeService.class);
        Map<String, Object> services = new HashMap<>();
        beansWithAnnotation.forEach((beanName, bean) -> services.put(getServiceName(bean), bean));
        register.register(services);
    }

    private String getServiceName(Object bean) {
        JokeService annotation = bean.getClass().getAnnotation(JokeService.class);
        return ServiceUtil.spliceServiceName(bean.getClass().getName(), annotation.version());
    }
}
