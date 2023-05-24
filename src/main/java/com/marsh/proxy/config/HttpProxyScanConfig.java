package com.marsh.proxy.config;

import com.marsh.proxy.binding.DataProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Marsh
 * @date 2021-11-29日 16:16
 */
@Configuration
public class HttpProxyScanConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    public HttpScannerConfigurer httpScannerConfigurer(){
        HttpScannerConfigurer hsc = new HttpScannerConfigurer();
        hsc.setAnnotationClass(DataProxy.class);
        HttpProxyConfiguration configuration = new HttpProxyConfiguration();
        configuration.setApplicationContext(applicationContext);
        hsc.setConfiguration(configuration);
        return hsc;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
