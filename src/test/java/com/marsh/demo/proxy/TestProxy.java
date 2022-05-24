package com.marsh.demo.proxy;

import cn.hutool.http.Method;
import com.marsh.demo.actuator.TestDataProxy;
import com.marsh.demo.convert.DataResponseConvert;
import com.marsh.demo.convert.DataResult;
import com.marsh.proxy.annotations.RequestQuery;
import com.marsh.proxy.binding.DataProxy;
import com.marsh.proxy.convert.ResponseHandler;

/**
 * 接口调用代理类（这个接口不需要写实现类），使用的时候直接@Autowired这个接口就可以了
 * @author Marsh
 * @date 2022-05-24
 */
@DataProxy
public interface TestProxy {


    @TestDataProxy(url= "/test",method = Method.GET,key = "get")
    @ResponseHandler(convert = DataResponseConvert.class)
    DataResult testGet(@RequestQuery(name = "msg") String msg);

    @TestDataProxy(url= "/test",method = Method.POST,key = "post")
    @ResponseHandler(convert = DataResponseConvert.class)
    DataResult testPost(@RequestQuery(name = "msg") String msg);

}
