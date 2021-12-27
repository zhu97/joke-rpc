package com.joke.register.nacos;

import com.alibaba.boot.nacos.config.properties.NacosConfigProperties;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.joke.properties.JokeRpcProperties;
import com.joke.register.Register;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NacosRegister implements Register {

    private class Service {
        private String host;

        private int port;

        private String serviceName;

        public Service(String serviceName, String host, int port) {
            this.serviceName = serviceName;
            this.host = host;
            this.port = port;
        }
    }

    private final Logger log = LoggerFactory.getLogger(NacosRegister.class);

    private NacosConfigProperties nacosConfigProperties;

    private JokeRpcProperties jokeRpcProperties;

    private final List<Service> services = new LinkedList<>();

    public NacosRegister(NacosConfigProperties nacosConfigProperties, JokeRpcProperties jokeRpcProperties) {
        this.nacosConfigProperties = nacosConfigProperties;
        this.jokeRpcProperties = jokeRpcProperties;
    }

    @SneakyThrows
    @Override
    public void register(Map<String, Object> serviceMap) {
        NamingService naming;
        try {
            naming = NamingFactory.createNamingService(nacosConfigProperties.getServerAddr());
        } catch (Exception exception) {
            log.error("Create nacosNaming error when Registering services!!");
            throw exception;
        }
        serviceMap.forEach((service, bean) -> {
            try {
                register(naming, service, bean);
            } catch (NacosException e) {
                log.error("register service [{}] error", service, e);
            }
        });
    }

    private void register(NamingService naming, String serviceName, Object bean) throws NacosException {
        Instance instance = produceInstance(bean);
        naming.registerInstance(serviceName, instance);
        log.info("register [bean={}] to nacos", bean.getClass().getName());
        services.add(new Service(serviceName, instance.getIp(), instance.getPort()));
    }

    private Instance produceInstance(Object bean) {
        String beanPathName = bean.getClass().getName();
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
        instanceMeta.put("beanType", beanPathName);
        instance.setMetadata(instanceMeta);
        return instance;
    }


    @Override
    public void unRegister() {
        try {
            NamingService naming = NamingFactory.createNamingService(nacosConfigProperties.getServerAddr());
            for (Service service : services) {
                naming.deregisterInstance(service.serviceName, service.host, service.port);
            }
        } catch (Exception exception) {
            log.warn("unRegistry service error", exception);
        }
    }
}
