package com.joke.util;

import org.springframework.util.Assert;

public class ServiceUtil {

    private static final String delimiter = "#";

    public static String spliceServiceName(String serviceName, String version) {
        Assert.notNull(serviceName, "服务名称不能为空");
        Assert.notNull(version, "版本号不能为空");
        return version.equals("") ? serviceName : serviceName + delimiter + version;
    }
}
