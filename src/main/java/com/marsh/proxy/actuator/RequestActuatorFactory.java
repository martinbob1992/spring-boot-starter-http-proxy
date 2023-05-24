package com.marsh.proxy.actuator;

import com.marsh.proxy.convert.ResponseConvert;

import java.lang.reflect.Method;

/**
 * 请求执行器工厂
 */
public interface RequestActuatorFactory {


    boolean support(Method method);

    RequestActuator getRequestActuator(Method method, Object[] args, ResponseConvert responseConvert);

}
