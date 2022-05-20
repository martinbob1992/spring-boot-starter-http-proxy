package com.marsh.proxy.actuator;

import cn.hutool.http.Method;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestProxy {

    String url();

    Method method();
}
