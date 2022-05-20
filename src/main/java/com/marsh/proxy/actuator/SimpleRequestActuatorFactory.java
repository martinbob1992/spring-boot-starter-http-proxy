package com.marsh.proxy.actuator;

import java.lang.reflect.Method;

public class SimpleRequestActuatorFactory implements RequestActuatorFactory{

    @Override
    public boolean support(Method method) {
        RequestProxy requestProxy = method.getAnnotation(RequestProxy.class);
        return requestProxy != null;
    }

    @Override
    public RequestActuator getRequestActuator(Method method) {
        RequestProxy requestProxy = method.getAnnotation(RequestProxy.class);
        return new SimpleRequestActuator(method,requestProxy.url(),requestProxy.method());
    }
}
