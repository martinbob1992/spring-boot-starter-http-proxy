package com.marsh.proxy.actuator;

import cn.hutool.http.Method;

public class SimpleRequestActuatorFactory extends SuperRequestActuatorFactory<RequestProxy,SimpleRequestActuator> implements RequestActuatorFactory{

    @Override
    protected String getUrl(RequestProxy proxy) {
        return proxy.url();
    }

    @Override
    protected Method getRequestMethod(RequestProxy proxy) {
        return proxy.method();
    }
}
