package com.marsh.demo.actuator;

import cn.hutool.http.HttpRequest;
import com.marsh.proxy.actuator.AbstractRequestActuator;
import com.marsh.proxy.actuator.ActuatorContext;
import com.marsh.proxy.exception.AuthProxyException;

/**
 * 代理处理器
 * @see TestDataActuatorFactory
 * @author Marsh
 * @date 2022-05-24日 15:27
 */
public class TestDataActuator extends AbstractRequestActuator<TestDataProxy> {

    /**
     * 由于处理器是单例的，切记这里初始化的数据都是final的，防止出现线程安全问题
     */
    public TestDataActuator(ActuatorContext<TestDataProxy> context) {
        super(context);
    }

    /**
     * 给当前请求添加授权头
     * @author Marsh
     * @param request
     * @throws AuthProxyException
     */
    @Override
    public void authentication(HttpRequest request) throws AuthProxyException {
        // 这个方法就是用来进行授权操作的，这里作为demo简单的写了一个头信息
        request.header("token","token");
        request.header("key",context.getProxy().key());
    }
}
