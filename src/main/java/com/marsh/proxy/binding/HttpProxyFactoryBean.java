package com.marsh.proxy.binding;

import lombok.Getter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 * 代理工厂
 * @author Marsh
 * @date 2021-11-29日 11:36
 */
public class HttpProxyFactoryBean<T> implements FactoryBean<T>, Serializable, InitializingBean {

    @Getter
    protected final Class<T> targetInterface;

    public HttpProxyFactoryBean(Class<T> targetInterface){
        this.targetInterface = targetInterface;
    }

    protected T newInstance(com.marsh.proxy.binding.HttpProxy<T> httpProxy) {
        return (T) Proxy.newProxyInstance(targetInterface.getClassLoader(), new Class[]{targetInterface}, httpProxy);
    }

    @Override
    public T getObject() throws Exception {
        return (T) newInstance(new com.marsh.proxy.binding.HttpProxy(targetInterface));
    }

    @Override
    public Class<?> getObjectType() {
        return targetInterface;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
