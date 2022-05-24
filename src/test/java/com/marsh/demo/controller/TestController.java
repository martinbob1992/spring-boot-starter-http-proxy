package com.marsh.demo.controller;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 模拟第三方提供的接口
 * @author Marsh
 * @date 2022-05-24日 14:25
 */
@RestController
public class TestController {

    /**
     * 这个接口只是用来模拟需要代理的接口，需要代理的接口一般都是第三方提供的接口，
     * 通常都需要进行接口鉴权，这里只是简单的模拟了一下鉴权但并不影响我们理解框架的使用
     * @param request
     * @param msg
     * @return
     */
    @RequestMapping(value="/test",method = {RequestMethod.GET,RequestMethod.POST})
    public String test(HttpServletRequest request,@RequestParam String msg){
        // 这里简单的模拟下访问接口必须要有token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)){
            return "{\"code\":401,\"msg\":\"无权访问\"}";
        }
        // 鉴权通过后进行实际接口的访问处理，这里仅简单返回了一个json字符串，
        // 这里故意没有返回对象是因为大部分情况第三方接口我们调用后得到的就是一个json字符串
        return "{\"code\":200,\"msg\":\""+msg+"\",\"key\":\""+request.getHeader("key")+"\"}";
    }
}
