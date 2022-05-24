package com.marsh.proxy.convert;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marsh
 * @date 2021-12-07日 9:51
 */
public class ResponseConvertBuild {

    private static final Map<Method, ResponseConvert> convertCache = new ConcurrentHashMap<>();

    public static <T extends ResponseConvert> ResponseConvert build(Method method){
        ResponseConvert responseConvert = convertCache.get(method);
        if (responseConvert != null){
            return responseConvert;
        }
        ResponseHandler handler = method.getAnnotation(ResponseHandler.class);
        if (handler == null){
            return build(method, DefaultResponseConvert.class);
        }
        return build(method,handler.convert());
    }

    public static <T extends ResponseConvert> ResponseConvert build(Method method, Class<T> clazz){
        ResponseConvert responseConvert = convertCache.get(method);
        if (responseConvert != null){
            return responseConvert;
        }
        return convertCache.computeIfAbsent(method, m -> {
            try {
                ResponseConvert rc = (ResponseConvert)clazz.newInstance();
                return rc;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(clazz.getName() + "缺少无参构造函数!");
            }
        });
    }
}
