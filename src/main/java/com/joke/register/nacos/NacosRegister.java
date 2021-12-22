package com.joke.register.nacos;

import com.alibaba.boot.nacos.config.properties.NacosConfigProperties;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.joke.properties.JokeRpcProperties;
import com.joke.register.Register;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class NacosRegister implements Register {

    private final Logger log = LoggerFactory.getLogger(NacosRegister.class);

    private NacosConfigProperties nacosConfigProperties;

    private JokeRpcProperties jokeRpcProperties;

    public NacosRegister(NacosConfigProperties nacosConfigProperties, JokeRpcProperties jokeRpcProperties) {
        this.nacosConfigProperties = nacosConfigProperties;
        this.jokeRpcProperties = jokeRpcProperties;
    }

    @Override
    public void register(Map<String, Object> beansWithAnnotation) {
        beansWithAnnotation.forEach((beanName, bean) -> {
            try {
                register(beanName, bean);
            } catch (NacosException e) {
                e.printStackTrace();
            }
        });
    }

    private void register(String beanName, Object bean) throws NacosException {
        String beanPathName = bean.getClass().getName();
        log.info("register [bean={}] to nacos", beanPathName);
        NamingService naming = NamingFactory.createNamingService(nacosConfigProperties.getServerAddr());
        String host;
        int port = jokeRpcProperties.getPort();
        if (jokeRpcProperties.getHost() == null || "".equals(jokeRpcProperties.getHost())) {
            try {
                InetAddress addr = InetAddress.getLocalHost();
                host = addr.getHostAddress();
            } catch (UnknownHostException e) {
                log.warn("get InetAddress error, use customer host", e);
                host = jokeRpcProperties.getHost();
            }
        } else {
            host = jokeRpcProperties.getHost();
        }
        Instance instance = new Instance();
        instance.setIp(host);
        instance.setPort(port);
        instance.setHealthy(true);
        Map<String, String> instanceMeta = new HashMap<>();
//        don`t know what should insert
        instanceMeta.put("bean", beanName);
        instanceMeta.put("beanType", beanPathName);
        instance.setMetadata(instanceMeta);
        naming.registerInstance(beanName, instance);
    }
}
