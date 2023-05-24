package com.marsh.proxy.actuator.impl;

import cn.hutool.http.Method;
import com.marsh.proxy.actuator.RequestActuatorFactory;
import com.marsh.proxy.actuator.SuperRequestActuatorFactory;

public class SimpleRequestActuatorFactory extends SuperRequestActuatorFactory<RequestProxy,SimpleRequestActuator> implements RequestActuatorFactory {

    @Override
    protected String getUrl(RequestProxy proxy) {
        return proxy.url();
    }

    @Override
    protected Method getRequestMethod(RequestProxy proxy) {
        return proxy.method();
    }
}
