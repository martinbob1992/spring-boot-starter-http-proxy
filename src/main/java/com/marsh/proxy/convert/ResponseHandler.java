package com.marsh.proxy.convert;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseHandler {

    Class<? extends ResponseConvert> convert() default DefaultResponseConvert.class;
}
