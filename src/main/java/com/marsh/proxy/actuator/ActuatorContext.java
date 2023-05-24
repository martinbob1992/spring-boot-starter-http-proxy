package com.marsh.proxy.actuator;

import com.marsh.proxy.convert.ResponseConvert;
import lombok.Data;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import cn.hutool.http.Method;
import java.util.Map;

@Data
public class ActuatorContext<T extends Annotation> implements Serializable {

    /**
     * 配置的请求地址
     */
    private String url;

    /**
     * 请求类型，get | post等
     */
    private Method requestMethod;



    /**
     * 当前执行器代理的是哪个方法
      */
    private java.lang.reflect.Method invokeMethod;
    /**
     * 当前执行器的请求转换器
     */
    private ResponseConvert responseConvert;
    /**
     * 当前执行器的请求参数
     */
    private Object[] params;
    /**
     * 方法上的代理信息注解
     */
    private T proxy;
    /**
     * 额外的参数信息
     */
    private Map<String,Object> customVariables;
}
