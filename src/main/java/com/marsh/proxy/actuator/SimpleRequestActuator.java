package com.marsh.proxy.actuator;

import cn.hutool.http.HttpRequest;


/**
 * @author Marsh
 * @date 2021-12-08日 9:20
 */
public class SimpleRequestActuator extends AbstractRequestActuator<RequestProxy> {
    public SimpleRequestActuator(ActuatorContext<RequestProxy> context) {
        super(context);
    }

    @Override
    public void authentication(HttpRequest request) {
        //空实现,不对请求做任何授权操作
    }
}
