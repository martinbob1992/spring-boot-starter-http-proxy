package com.marsh.demo.convert;

import cn.hutool.http.HttpResponse;
import com.google.gson.Gson;
import com.marsh.proxy.convert.ResponseConvert;
import com.marsh.proxy.exception.ConvertProxyException;
import com.marsh.proxy.exception.DataProxyException;

/**
 * 数据结果的转换类，这个类必须要有无参构造方法
 * ps：这个类在实际使用中是单例的
 * @author Marsh
 * @date 2022-05-24日 14:37
 */
public class DataResponseConvert implements ResponseConvert<DataResult> {

    private Gson gson = new Gson();

    @Override
    public DataResult convert(HttpResponse response) throws ConvertProxyException {
        if (!response.isOk()) {
            throw new DataProxyException(response.getStatus(), response.body());
        }
        String body = response.body();
        return gson.fromJson(body,DataResult.class);
    }
}
