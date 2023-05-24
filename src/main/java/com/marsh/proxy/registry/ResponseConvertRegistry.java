package com.marsh.proxy.registry;

import com.marsh.proxy.config.HttpProxyConfiguration;
import com.marsh.proxy.convert.DefaultResponseConvert;
import com.marsh.proxy.convert.ResponseConvert;
import com.marsh.proxy.convert.ResponseHandler;
import com.marsh.proxy.util.ClassUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ResponseConvertRegistry {

    private HttpProxyConfiguration configuration;

    /**
     * 记录方法结果转换器缓存
     */
    private final Map<Method, ResponseConvert> CONVERT_CACHE = new HashMap<>();
    private final Map<Class<? extends ResponseConvert>,ResponseConvert> INSTANCE_CACHE = new HashMap<>();

    public ResponseConvertRegistry(HttpProxyConfiguration configuration){
        this.configuration = configuration;
    }

    public void addResponseConvert(Method method){
        if (CONVERT_CACHE.containsKey(method)){
            // 已经注册过就不重复注册了
            return;
        }
        ResponseHandler handler = method.getAnnotation(ResponseHandler.class);
        if (handler == null){
            //如果没有指定则使用默认转换器
            registry(method, DefaultResponseConvert.class);
        } else {
            registry(method, handler.convert());
        }
    }

    public ResponseConvert getResponseConvert(Method method){
        return CONVERT_CACHE.get(method);
    }

    private void registry(Method method,Class<? extends ResponseConvert> convertClass){
        if (INSTANCE_CACHE.containsKey(convertClass)){
            CONVERT_CACHE.put(method,INSTANCE_CACHE.get(convertClass));
            return;
        }
        ResponseConvert responseConvert = ClassUtil.newInstance(configuration.getApplicationContext(), convertClass);
        CONVERT_CACHE.put(method,responseConvert);
        INSTANCE_CACHE.put(convertClass,responseConvert);
    }


}
