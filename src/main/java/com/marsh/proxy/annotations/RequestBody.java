package com.marsh.proxy.annotations;

import cn.hutool.http.ContentType;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestBody {

    ContentType contentType() default ContentType.JSON;
}
