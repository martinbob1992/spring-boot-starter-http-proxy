package com.marsh.proxy.binding;

import com.marsh.proxy.convert.ResponseHandler;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marsh
 * @date 2021-12-02日 16:32
 */
public class HttpProxyMethodInvokerBuilder {


    public static Map<Method, HttpProxy.HttpProxyMethodInvoker> build(Class targetInterface){
        if (!targetInterface.isInterface()){
            throw new RuntimeException(targetInterface + "不是一个接口类型!");
        }
        DataProxy dataProxy = AnnotationUtils.findAnnotation(targetInterface, DataProxy.class);
        if (dataProxy == null){
            throw new RuntimeException(targetInterface + "没有添加@DataProxy注解");
        }
        Method[] declaredMethods = targetInterface.getDeclaredMethods();
        Map<Method, HttpProxy.HttpProxyMethodInvoker> result = new HashMap<>();
        if (declaredMethods != null && declaredMethods.length > 0){
            for (Method method : declaredMethods){
                ResponseHandler responseHandler = method.getAnnotation(ResponseHandler.class);
                if (responseHandler == null && !method.getReturnType().isAssignableFrom(String.class)){
                    // 目前没有做返回结果转换，仅支持string返回结果
                    // 没有指定ResponseHandler时会使用默认转换器DefaultResponseConvert,而默认转换器返回结果是String
                    throw new RuntimeException(targetInterface + "."+method.getName()+"返回结果必须必须使用String类型");
                }
                result.put(method,new HttpProxy.DefaultMethodInvoker(new HttpProxyMethod(method)));
            }
        }
        return result;
    }


}
