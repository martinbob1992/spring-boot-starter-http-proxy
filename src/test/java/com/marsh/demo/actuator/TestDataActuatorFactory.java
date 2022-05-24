package com.marsh.demo.actuator;

import cn.hutool.core.util.StrUtil;
import com.marsh.proxy.actuator.RequestActuator;
import com.marsh.proxy.actuator.RequestActuatorFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 请求处理器的工厂类，主要用于创建@DataReportingProxy注解标注的方法实际代理类
 * @author Marsh
 * @date 2022-05-24日 15:27
 */
@Component
public class TestDataActuatorFactory implements RequestActuatorFactory, ApplicationContextAware {


    private ApplicationContext applicationContext;
    private String serverIpCache;

    @Override
    public boolean support(Method method) {
        TestDataProxy requestProxy = method.getAnnotation(TestDataProxy.class);
        return requestProxy != null;
    }

    public RequestActuator getRequestActuator(Method method) {
        TestDataProxy requestProxy = method.getAnnotation(TestDataProxy.class);
        //从配置文件中读取marsh.serverIp,大部分情况下测试环境和生产环境接口访问肯定是不同的,这里将差异通过构造方法直接传递到执行器中
        //这里作为demo仅简单这样处理了下，实际情况下使用@ConfigurationProperties单独封装一个配置类将所有参数都维护在一个类中会更好
        if (StrUtil.isBlank(serverIpCache)){
            serverIpCache = applicationContext.getEnvironment().getProperty("marsh.serverIp");
        }
        return new TestDataActuator(serverIpCache,method, requestProxy);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
