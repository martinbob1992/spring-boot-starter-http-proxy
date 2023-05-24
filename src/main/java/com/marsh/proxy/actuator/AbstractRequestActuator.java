package com.marsh.proxy.actuator;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.marsh.proxy.exception.AuthProxyException;
import com.marsh.proxy.util.HttpClientUtil;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;

/**
 * @author Marsh
 * @date 2021-12-06日 11:56
 */
public abstract class AbstractRequestActuator<T extends Annotation> implements RequestActuator {

    protected ActuatorContext<T> context;
    public AbstractRequestActuator(ActuatorContext<T> context){
        this.context = context;
    }

    /**
     * 获取一个基础的http请求
     * @author Marsh
     * @date 2021-12-07
     * @return cn.hutool.http.HttpRequest
     */
    public HttpRequest getBaseHttpRequest(){
        return HttpClientUtil.buildBaseRequest(context);
    }

    public abstract void authentication(HttpRequest request) throws AuthProxyException;

    public Object convert(HttpResponse response){
        return context.getResponseConvert().convert(response);
    }

    @Override
    @SneakyThrows
    public Object execute(){
        HttpRequest request = getBaseHttpRequest();
        // 给当前请求添加授权信息
        authentication(request);
        HttpResponse response = HttpClientUtil.execute(request);
        return convert(response);
    }
}
