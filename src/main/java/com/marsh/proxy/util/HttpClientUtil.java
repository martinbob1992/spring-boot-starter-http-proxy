package com.marsh.proxy.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.google.gson.Gson;
import com.marsh.proxy.actuator.ActuatorContext;
import com.marsh.proxy.annotations.RequestBody;
import com.marsh.proxy.annotations.RequestFromData;
import com.marsh.proxy.annotations.RequestHeader;
import com.marsh.proxy.annotations.RequestQuery;
import com.marsh.proxy.constant.ValueConstants;
import com.marsh.proxy.exception.DataProxyException;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @author Marsh
 * @date 2021-12-06日 18:30
 */
@Slf4j
public class HttpClientUtil {

    private static Gson gson = new Gson();

    public static HttpResponse execute(HttpRequest request){
        if (log.isDebugEnabled()){
            Map<String, Object> form = request.form();
            if (form != null && form.size() > 0){
                StringBuilder sb = new StringBuilder(request.toString());
                sb.append("Form Data:\r\n");
                form.forEach((k,v) -> {
                    sb.append("\t").append(k).append(": ").append(v).append("\r\n");
                });
                log.debug(sb.toString());
            } else {
                log.debug(request.toString());
            }
        }
        HttpResponse response = request.execute();
        if (log.isDebugEnabled()){
            log.debug("请求状态:{},返回结果:{}",response.getStatus(),response.body());
        }
        return response;
    }

    public static <T> T execute(HttpRequest request, Class<T> clazz) throws DataProxyException {
        HttpResponse response = HttpClientUtil.execute(request);
        if (!response.isOk()){
            throw new DataProxyException(response.getStatus(),response.body());
        }
        if (String.class.isAssignableFrom(clazz)){
            return (T)response.body();
        }
        return gson.fromJson(response.body(),clazz);
    }

    /**
     * 构建一个基础的请求类型
     * @author Marsh
     * @date 2021-12-06
     * @param url
     * @param method
     * @return org.apache.http.client.methods.HttpRequestBase
     */
    public static HttpRequest buildBaseRequest(String url, Method method){
        return buildBaseRequest(url,method,null,null);
    }

    public static <T extends Annotation> HttpRequest buildBaseRequest(ActuatorContext<T> context){
        return buildBaseRequest(context.getUrl(),context.getRequestMethod(),context.getInvokeMethod(),context.getParams());
    }

    public static HttpRequest buildBaseRequest(String url, Method requestMethod,java.lang.reflect.Method method,Object[] params){
        HttpRequest request = HttpUtil.createRequest(requestMethod,url);
        if (method == null){
            return request;
        }
        StringBuilder queryParams = new StringBuilder();
        Parameter[] parameters = method.getParameters();
        /**
         * @TODO 1.目前仅针对系统写好的注解进行解析，后续添加自定义注解解析流程
         * 2.根据method参数建立元数据缓存，避免每次都重新判断怎么解析参数
         */
        if (parameters != null && parameters.length > 0){
            for (int i = 0;i<parameters.length;i++){
                Parameter parameter = parameters[i];
                RequestBody body = parameter.getAnnotation(RequestBody.class);
                RequestHeader header = parameter.getAnnotation(RequestHeader.class);
                RequestQuery query = parameter.getAnnotation(RequestQuery.class);
                RequestFromData fromData = parameter.getAnnotation(RequestFromData.class);
                if (body != null && params[i] != null){
                    request.body(params[i].toString(),body.contentType().getValue());
                } else if (header != null){
                    String paramName = header.name();
                    if (StrUtil.isBlank(paramName)){
                        paramName = parameter.getName();
                    }
                    if (params[i] == null){
                        if (!ValueConstants.DEFAULT_NONE.equals(header.defaultValue())){
                            request.header(paramName,header.defaultValue());
                        } else if (header.required()) {
                            // 找不到这个参数的值,但是声明了必须填写
                            throw new RuntimeException(method.getDeclaringClass().getName() + "." + method.getName() + "["+paramName+"] required=true");
                        }
                    } else {
                        request.header(paramName,params[i].toString());
                    }
                } else if (fromData != null){
                    String paramName = fromData != null ? fromData.name() : null;
                    if (StrUtil.isBlank(paramName)){
                        paramName = parameter.getName();
                    }
                    if (params[i] == null){
                        if (!ValueConstants.DEFAULT_NONE.equals(fromData.defaultValue())){
                            request.form(paramName,fromData.defaultValue());
                        } else if (fromData.required()) {
                            // 找不到这个参数的值,但是声明了必须填写
                            throw new RuntimeException(method.getDeclaringClass().getName() + "." + method.getName() + "["+paramName+"] required=true");
                        }
                    } else {
                        request.form(paramName,params[i]);
                    }
                } else {
                    String paramName = query != null ? query.name() : null;
                    if (StrUtil.isBlank(paramName)){
                        paramName = parameter.getName();
                    }
                    if (params[i] == null){
                        if (!ValueConstants.DEFAULT_NONE.equals(query.defaultValue())){
                            if (queryParams.length() == 0){
                                if (!url.contains("?")){
                                    queryParams.append("?");
                                } else {
                                    queryParams.append("&");
                                }
                            } else {
                                queryParams.append("&");
                            }
                            queryParams.append(paramName).append("=").append(query.defaultValue());
                        } else if (query.required()) {
                            // 找不到这个参数的值,但是声明了必须填写
                            throw new RuntimeException(method.getDeclaringClass().getName() + "." + method.getName() + "["+paramName+"] required=true");
                        }
                    } else {
                        if (queryParams.length() == 0){
                            if (!url.contains("?")){
                                queryParams.append("?");
                            } else {
                                queryParams.append("&");
                            }
                        } else {
                            queryParams.append("&");
                        }
                        queryParams.append(paramName).append("=").append(params[i].toString());
                    }
                }
            }
        }
        if (queryParams.length() > 0){
            request.setUrl(url + queryParams.toString());
        }
        return request;
    }

}
