package com.marsh.demo.actuator;

import cn.hutool.http.Method;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestDataProxy {
    /**
     * 接口访问的url地址
     * @return
     */
    String url();

    /**
     * 接口的请求方式
     * @return
     */
    Method method();

    /**
     * 模拟访问接口需要传入key
     */
    String key();
}
