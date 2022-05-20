package com.marsh.proxy.actuator;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.marsh.proxy.convert.ResponseConvert;
import com.marsh.proxy.convert.ResponseConvertBuild;
import com.marsh.proxy.exception.AuthProxyException;
import com.marsh.proxy.util.HttpClientUtil;
import lombok.SneakyThrows;

/**
 * @author Marsh
 * @date 2021-12-06日 11:56
 */
public abstract class AbstractRequestActuator implements RequestActuator {

    protected final java.lang.reflect.Method method;
    protected final String url;
    protected final Method requestMethod;

    public AbstractRequestActuator(java.lang.reflect.Method method, String url, Method requestMethod){
        this.method = method;
        this.url = url;
        this.requestMethod = requestMethod;
    }

    public ResponseConvert getResponseConvert(){
        return ResponseConvertBuild.build(this.method);
    }

    /**
     * 获取一个基础的http请求
     * @author Marsh
     * @date 2021-12-07
     * @return cn.hutool.http.HttpRequest
     */
    public HttpRequest getBaseHttpRequest(Object[] params){
        return HttpClientUtil.buildBaseRequest(this.url, this.requestMethod,method,params);
    }

    public abstract void authentication(HttpRequest request) throws AuthProxyException;


    @Override
    @SneakyThrows
    public Object execute(Object[] params){
        HttpRequest request = getBaseHttpRequest(params);
        // 给当前请求添加授权信息
        authentication(request);
        return getResponseConvert().convert(HttpClientUtil.execute(request));
    }
}
