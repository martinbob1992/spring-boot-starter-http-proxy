package com.marsh.proxy.registry;

import com.marsh.proxy.binding.HttpProxy;
import com.marsh.proxy.config.HttpProxyConfiguration;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HttpProxyMethodInvokerRegistry {

    private HttpProxyConfiguration configuration;

    private final Map<Method, HttpProxy.HttpProxyMethodInvoker> METHOD_INVOKER_CACHE = new HashMap<>();

    public HttpProxyMethodInvokerRegistry(HttpProxyConfiguration configuration){
        this.configuration = configuration;
    }

    public HttpProxy.HttpProxyMethodInvoker getMethodInvoker(Method method){
        HttpProxy.HttpProxyMethodInvoker httpProxyMethodInvoker = METHOD_INVOKER_CACHE.get(method);
        return httpProxyMethodInvoker;
    }

    public void addMethodInvoker(Method method,HttpProxy.HttpProxyMethodInvoker methodInvoker){
        METHOD_INVOKER_CACHE.put(method,methodInvoker);
    }

}
