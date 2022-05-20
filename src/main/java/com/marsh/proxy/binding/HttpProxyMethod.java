package com.marsh.proxy.binding;

import com.marsh.proxy.actuator.RequestActuator;
import com.marsh.proxy.actuator.RequestActuatorFactoryBean;

import java.lang.reflect.Method;

/**
 * @author Marsh
 * @date 2021-12-02日 16:54
 */
public class HttpProxyMethod {

    private final RequestActuator requestActuator;
    private final Method method;

    public HttpProxyMethod(Method method){
        this.method = method;
        this.requestActuator = RequestActuatorFactoryBean.getRequestActuator(method);
    }

    public Object execute(Object[] args){
        return requestActuator.execute(args);
    }
}
