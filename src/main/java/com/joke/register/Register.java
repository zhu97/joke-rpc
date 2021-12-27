package com.joke.register;

import java.util.Map;

public interface Register {

    void register(Map<String, Object> serviceMap);

    void unRegister();
}
