package com.marsh.proxy.convert;

import cn.hutool.http.HttpResponse;
import com.marsh.proxy.exception.ConvertProxyException;

/**
 * @author martin
 * @date 2021年11月30日 8:54
 */
public interface ResponseConvert<T> {

    T convert(HttpResponse response) throws ConvertProxyException;
}
