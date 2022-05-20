package com.marsh.proxy.config;

import com.marsh.proxy.binding.DataProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Marsh
 * @date 2021-11-29日 16:16
 */
@Configuration
public class HttpProxyScanConfig {

    @Bean
    public HttpScannerConfigurer httpScannerConfigurer(){
        HttpScannerConfigurer hsc = new HttpScannerConfigurer();
        hsc.setAnnotationClass(DataProxy.class);
        return hsc;
    }
}
