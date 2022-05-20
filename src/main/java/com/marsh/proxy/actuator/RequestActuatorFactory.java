package com.marsh.proxy.actuator;

import java.lang.reflect.Method;

/**
 * 请求执行器工厂
 */
public interface RequestActuatorFactory {


    boolean support(Method method);

    RequestActuator getRequestActuator(Method method);

}
