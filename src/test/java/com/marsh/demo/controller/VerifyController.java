package com.marsh.demo.controller;

import com.marsh.demo.convert.DataResult;
import com.marsh.demo.proxy.TestProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证接口代理是否可用
 * @author Marsh
 * @date 2022-05-24日 14:25
 */
@RestController
public class VerifyController {

    @Autowired
    private TestProxy proxy;

    @RequestMapping(value="/t1",method = RequestMethod.GET)
    public DataResult t1(@RequestParam(required = false,defaultValue = "") String msg){
        return proxy.testGet(msg);
    }

    @RequestMapping(value="/t2",method = RequestMethod.GET)
    public DataResult t2(@RequestParam(required = false,defaultValue = "") String msg){
        return proxy.testPost(msg);
    }

}
