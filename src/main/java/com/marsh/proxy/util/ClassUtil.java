package com.marsh.proxy.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;

public class ClassUtil {

    public static <T>T newInstance(ApplicationContext context,Class<T> clazz){
        if (clazz.isInterface() || clazz.isAnnotation()){
            throw new RuntimeException("接口或者注解无法通过该方法实例化!"+clazz.toString());
        }
        T bean = null;
        try{
            bean = context.getBean(clazz);
        } catch (BeansException e){
        }
        if (bean != null){
            // 优先有spring 容器里面找这个类
            return bean;
        }
        // spring中找不到则通过构造方法进行类初始化
        Constructor<T> resolvableConstructor = BeanUtils.getResolvableConstructor(clazz);
        Class<?>[] args = resolvableConstructor.getParameterTypes();
        if (args.length == 0){
            return BeanUtils.instantiateClass(resolvableConstructor);
        }
        Object[] argsValues = new Object[args.length];
        for (int i = 0 ; i < args.length; i++) {
            Object arg = context.getBean(args[i]);
            if (arg == null){
                throw new RuntimeException(clazz.toString()+"构造函数需要的参数"+args[i].toString()+"在spring容器中未找到!");
            }
            argsValues[i] = arg;
        }
        return BeanUtils.instantiateClass(resolvableConstructor,argsValues);
    }

    public static <T>T newInstance(Class<T> clazz,Object... args){
        if (args == null || args.length == 0){

        }
        Constructor<?>[] ctors = clazz.getConstructors();
        if (ctors == null || ctors.length == 0){
            throw new RuntimeException(clazz.toString()+"缺少构造函数!");
        }
        Optional<Constructor<?>> optional = Arrays.stream(ctors).filter(c -> {
            if (c.getParameterCount() == args.length) {
                if (c.getParameterCount() == 0) {
                    return true;
                }
                for (int i = 0; i < c.getParameterTypes().length; i++) {
                    Class<?> parameterType = c.getParameterTypes()[i];
                    if (!parameterType.equals(args[i].getClass())) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }).findFirst();
        if (!optional.isPresent()){
            throw new RuntimeException(clazz.toString()+"构造函数无法匹配!");
        }
        return (T) BeanUtils.instantiateClass(optional.get(),args);
    }

}
