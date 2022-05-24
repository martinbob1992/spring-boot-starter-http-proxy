package com.marsh.proxy.actuator;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;

/**
 * @author Marsh
 * @date 2021-12-08日 9:20
 */
public class SimpleRequestActuator extends AbstractRequestActuator {

    public SimpleRequestActuator(java.lang.reflect.Method method, String url, Method requestMethod) {
        super(method, url, requestMethod);
    }

    @Override
    public void authentication(HttpRequest request) {
        //空实现,不对请求做任何授权操作
    }
}
