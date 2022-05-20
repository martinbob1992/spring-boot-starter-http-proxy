package com.marsh.proxy.convert;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marsh
 * @date 2021-12-07日 9:51
 */
public class ResponseConvertBuild {

    private static final Map<Method, com.marsh.proxy.convert.ResponseConvert> convertCache = new ConcurrentHashMap<>();

    public static <T extends com.marsh.proxy.convert.ResponseConvert> com.marsh.proxy.convert.ResponseConvert build(Method method){
        com.marsh.proxy.convert.ResponseConvert responseConvert = convertCache.get(method);
        if (responseConvert != null){
            return responseConvert;
        }
        com.marsh.proxy.convert.ResponseHandler handler = method.getAnnotation(com.marsh.proxy.convert.ResponseHandler.class);
        if (handler == null){
            return build(method, com.marsh.proxy.convert.DefaultResponseConvert.class);
        }
        return build(method,handler.convert());
    }

    public static <T extends com.marsh.proxy.convert.ResponseConvert> com.marsh.proxy.convert.ResponseConvert build(Method method, Class<T> clazz){
        com.marsh.proxy.convert.ResponseConvert responseConvert = convertCache.get(method);
        if (responseConvert != null){
            return responseConvert;
        }
        return convertCache.computeIfAbsent(method, m -> {
            try {
                com.marsh.proxy.convert.ResponseConvert rc = (com.marsh.proxy.convert.ResponseConvert)clazz.newInstance();
                return rc;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(clazz.getName() + "缺少无参构造函数!");
            }
        });
    }
}
