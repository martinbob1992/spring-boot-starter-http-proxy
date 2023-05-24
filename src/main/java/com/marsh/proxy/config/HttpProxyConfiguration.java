package com.marsh.proxy.config;

import com.marsh.proxy.binding.DataProxy;
import com.marsh.proxy.binding.HttpProxy;
import com.marsh.proxy.binding.HttpProxyMethod;
import com.marsh.proxy.convert.DefaultResponseConvert;
import com.marsh.proxy.convert.ResponseConvert;
import com.marsh.proxy.convert.ResponseHandler;
import com.marsh.proxy.registry.HttpProxyMethodInvokerRegistry;
import com.marsh.proxy.registry.ResponseConvertRegistry;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HttpProxyConfiguration {

    @Getter
    @Setter
    private ApplicationContext applicationContext;

    private Map<Class,HttpProxy> httpProxyMap = new HashMap<>();

    private ResponseConvertRegistry responseConvertRegistry = new ResponseConvertRegistry(this);
    private HttpProxyMethodInvokerRegistry httpProxyMethodInvokerRegistry = new HttpProxyMethodInvokerRegistry(this);

    public void addResponseConvert(Method method){
        responseConvertRegistry.addResponseConvert(method);
    }
    public ResponseConvert getResponseConvert(Method method){
        return responseConvertRegistry.getResponseConvert(method);
    }

    public HttpProxy.HttpProxyMethodInvoker getMethodInvoker(Method method){
        return httpProxyMethodInvokerRegistry.getMethodInvoker(method);
    }

    public void addMethodInvoker(Method method,HttpProxy.HttpProxyMethodInvoker methodInvoker){
        httpProxyMethodInvokerRegistry.addMethodInvoker(method,methodInvoker);
    }


    public <T> HttpProxy getHttpProxy(Class<T> targetInterface) {
        if (httpProxyMap.containsKey(targetInterface)){
            return httpProxyMap.get(targetInterface);
        }
        if (!targetInterface.isInterface()){
            throw new RuntimeException(targetInterface + "不是一个接口类型!");
        }
        DataProxy dataProxy = AnnotationUtils.findAnnotation(targetInterface, DataProxy.class);
        if (dataProxy == null){
            throw new RuntimeException(targetInterface + "没有添加@DataProxy注解");
        }
        Method[] declaredMethods = targetInterface.getDeclaredMethods();
        if (declaredMethods != null && declaredMethods.length > 0){
            for (Method method : declaredMethods){
                ResponseHandler responseHandler = method.getAnnotation(ResponseHandler.class);
                if (responseHandler == null && !method.getReturnType().isAssignableFrom(String.class)){
                    // 目前没有做返回结果转换，仅支持string返回结果
                    // 没有指定ResponseHandler时会使用默认转换器DefaultResponseConvert,而默认转换器返回结果是String
                    throw new RuntimeException(targetInterface + "."+method.getName()+"返回结果必须必须使用String类型");
                }
                addResponseConvert(method);
                addMethodInvoker(method,new HttpProxy.DefaultMethodInvoker(new HttpProxyMethod(method,this)));
            }
        }
        HttpProxy httpProxy = new HttpProxy(targetInterface, this);
        httpProxyMap.put(targetInterface,httpProxy);
        return httpProxy;
    }
}
