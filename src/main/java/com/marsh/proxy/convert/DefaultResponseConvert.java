package com.marsh.proxy.convert;

import cn.hutool.http.HttpResponse;
import com.marsh.proxy.exception.ConvertProxyException;
import com.marsh.proxy.exception.DataProxyException;

/**
 * @author Marsh
 * @date 2021-11-30日 8:56
 */
public class DefaultResponseConvert implements ResponseConvert<String> {

    /**
     * 检验返回结果是否正常
     * @author Marsh
     * @date 2021-12-16
     * @param response
     * @return boolean
     */
    public void verification(HttpResponse response) throws DataProxyException {
        if (!response.isOk()){
            throw new DataProxyException(response.getStatus(),response.body());
        }
    }

    @Override
    public String convert(HttpResponse response) throws ConvertProxyException {
        verification(response);
        return response.body();
    }
}
