package com.marsh.proxy.binding;

import com.marsh.proxy.config.HttpProxyConfiguration;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Marsh
 * @date 2021-11-29日 13:38
 */
public class HttpProxy<T> implements InvocationHandler, Serializable {

    private final Class<T> targetInterface;
    private HttpProxyConfiguration configuration;

    public HttpProxy(Class<T> targetInterface,HttpProxyConfiguration configuration) {
        this.targetInterface = targetInterface;
        this.configuration = configuration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            return cachedInvoker(method).invoke(proxy, method, args);
        }
    }

    private HttpProxyMethodInvoker cachedInvoker(Method method) throws Throwable {
        HttpProxyMethodInvoker invoker = configuration.getMethodInvoker(method);
        if (invoker != null) {
            return invoker;
        }
        throw new RuntimeException("未找到代理方法!");
    }


    public interface HttpProxyMethodInvoker {
        Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
    }

    public static class DefaultMethodInvoker implements HttpProxyMethodInvoker {

        private final HttpProxyMethod httpProxyMethod;

        public DefaultMethodInvoker(HttpProxyMethod httpProxyMethod) {
            super();
            this.httpProxyMethod = httpProxyMethod;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return httpProxyMethod.execute(args);
        }
    }
}
