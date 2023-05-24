package com.marsh.proxy.binding;

import com.marsh.proxy.actuator.RequestActuatorFactory;
import com.marsh.proxy.config.HttpProxyConfiguration;
import com.marsh.proxy.convert.ResponseConvert;

import java.lang.reflect.Method;

/**
 * @author Marsh
 * @date 2021-12-02日 16:54
 */
public class HttpProxyMethod {

    HttpProxyConfiguration configuration;
    private final RequestActuatorFactory requestActuatorFactory;
    private final Method method;

    private final ResponseConvert responseConvert;

    public HttpProxyMethod(Method method, HttpProxyConfiguration configuration){
        this.method = method;
        this.configuration = configuration;
        requestActuatorFactory = configuration.getApplicationContext()
                .getBeansOfType(RequestActuatorFactory.class).values()
                .stream().filter(v -> v.support(method)).findFirst().get();
        this.responseConvert = configuration.getResponseConvert(method);
    }

    public Object execute(Object[] args){
        return requestActuatorFactory.getRequestActuator(method,args,responseConvert).execute();
    }
}
