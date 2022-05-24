package com.marsh.demo.actuator;

import cn.hutool.http.HttpRequest;
import com.marsh.proxy.actuator.AbstractRequestActuator;
import com.marsh.proxy.binding.HttpProxy;
import com.marsh.proxy.binding.HttpProxyMethodInvokerBuilder;
import com.marsh.proxy.exception.AuthProxyException;

/**
 * 代理处理器
 * @see TestDataActuatorFactory
 * @author Marsh
 * @date 2022-05-24日 15:27
 */
public class TestDataActuator extends AbstractRequestActuator {

    /**
     * 由于处理器是单例的，切记这里初始化的数据都是final的，防止出现线程安全问题
     */
    private final String key;

    /**
     * 这个处理器针对每一个method都是单例的，所以在这个处理器中要注意线程安全问题
     * 可以通过构造方法适当的传入
     * @see HttpProxy#HttpProxy(Class)
     * @see HttpProxyMethodInvokerBuilder#build(Class) 针对每个标记了@DataProxy的接口类每个方法动态生成代理类
     * @param serverIp
     * @param method
     * @param requestProxy
     */
    public TestDataActuator(String serverIp,java.lang.reflect.Method method, TestDataProxy requestProxy){
        super(method,serverIp+requestProxy.url(),requestProxy.method());
        this.key = requestProxy.key();
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
        request.header("key",key);
    }
}
