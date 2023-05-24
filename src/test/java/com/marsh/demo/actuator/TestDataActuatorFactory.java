package com.marsh.demo.actuator;

import cn.hutool.http.Method;
import com.marsh.proxy.actuator.RequestActuatorFactory;
import com.marsh.proxy.actuator.SuperRequestActuatorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 请求处理器的工厂类，主要用于创建@DataReportingProxy注解标注的方法实际代理类
 * @author Marsh
 * @date 2022-05-24日 15:27
 */
@Component
public class TestDataActuatorFactory extends SuperRequestActuatorFactory<TestDataProxy,TestDataActuator> implements RequestActuatorFactory {


    @Value("${marsh.serverIp:}")
    private String serverIpCache;


    @Override
    protected String getUrl(TestDataProxy proxy) {
        return serverIpCache + proxy.url();
    }

    @Override
    protected Method getRequestMethod(TestDataProxy proxy) {
        return proxy.method();
    }
}
