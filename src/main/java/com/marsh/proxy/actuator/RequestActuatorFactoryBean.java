package com.marsh.proxy.actuator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RequestActuatorFactoryBean {

    private static final List<com.marsh.proxy.actuator.RequestActuatorFactory> REQUEST_ACTUATOR_FACTORY_LIST = new ArrayList<>();
    private static final com.marsh.proxy.actuator.RequestActuatorFactory DEFAULT_REQUEST_ACTUATOR_FACTORY = new com.marsh.proxy.actuator.SimpleRequestActuatorFactory();

    public static synchronized boolean addRequestActuatorFactory(com.marsh.proxy.actuator.RequestActuatorFactory factory){
        return REQUEST_ACTUATOR_FACTORY_LIST.add(factory);
    }

    public static RequestActuator getRequestActuator(Method method){
        for (com.marsh.proxy.actuator.RequestActuatorFactory factory : REQUEST_ACTUATOR_FACTORY_LIST){
            if (factory.support(method)){
                return factory.getRequestActuator(method);
            }
        }
        if (DEFAULT_REQUEST_ACTUATOR_FACTORY.support(method)){
            return DEFAULT_REQUEST_ACTUATOR_FACTORY.getRequestActuator(method);
        }
        throw new RuntimeException(method.getDeclaringClass() + " 无法找到代理工厂!");
    }


}
