package com.marsh.proxy.actuator;

import cn.hutool.core.util.TypeUtil;
import com.marsh.proxy.convert.ResponseConvert;
import com.marsh.proxy.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class SuperRequestActuatorFactory<T extends Annotation,A extends AbstractRequestActuator<T>> implements RequestActuatorFactory {

    @Override
    public boolean support(Method method) {
        T proxy = getProxyAnnotation(method);
        return proxy != null;
    }

    /**
     * 获取当前类代理的注解泛型
     * @return
     */
    protected T getProxyAnnotation(Method method){
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)){
            throw new RuntimeException(String.format("%s 类缺少必要的泛型参数,需要明确该工厂能处理的注解参数!",this.getClass().toString()));
        }
        T proxy = (T) method.getAnnotation((Class<? extends Annotation>)TypeUtil.getClass(((ParameterizedType)genericSuperclass).getActualTypeArguments()[0]));
        return proxy;
    }

    protected Class<A> getRequestActuatorClass(){
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)){
            throw new RuntimeException(String.format("%s 类缺少必要的泛型参数,需要明确该工厂能处理的注解参数!",this.getClass().toString()));
        }
        Class<A> requestActuator = (Class<A>)TypeUtil.getClass(((ParameterizedType)genericSuperclass).getActualTypeArguments()[1]);
        return requestActuator;
    }

    protected abstract String getUrl(T proxy);

    protected abstract cn.hutool.http.Method getRequestMethod(T proxy);

    protected Map<String,Object> getCustomVariables(T proxy){
        return new HashMap<>();
    }

    public ActuatorContext<T> getActuatorContext(Method method,Object[] args, ResponseConvert responseConvert){
        ActuatorContext<T> context = new ActuatorContext<>();
        context.setParams(args);
        context.setInvokeMethod(method);
        context.setResponseConvert(responseConvert);
        T proxy = getProxyAnnotation(method);
        context.setProxy(proxy);
        context.setUrl(getUrl(proxy));
        context.setRequestMethod(getRequestMethod(proxy));
        context.setCustomVariables(getCustomVariables(proxy));
        return context;
    }

    @Override
    public RequestActuator getRequestActuator(Method method,Object[] args, ResponseConvert responseConvert){
        Class<A> requestActuatorClass = getRequestActuatorClass();
        ActuatorContext context = getActuatorContext(method,args,responseConvert);
        return ClassUtil.newInstance(requestActuatorClass,context);
    }

}
