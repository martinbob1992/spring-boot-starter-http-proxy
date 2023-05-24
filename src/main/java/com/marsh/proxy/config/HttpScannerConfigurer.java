package com.marsh.proxy.config;

import cn.hutool.core.annotation.AnnotationUtil;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;

/**
 * @author Marsh
 * @date 2021-11-29日 9:03
 */
public class HttpScannerConfigurer implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {


    private ApplicationContext applicationContext;

    @Setter
    private Class<? extends Annotation> annotationClass;

    @Setter
    private HttpProxyConfiguration configuration;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String[] beanNamesForAnnotation = applicationContext.getBeanNamesForAnnotation(SpringBootApplication.class);
        if (beanNamesForAnnotation == null || beanNamesForAnnotation.length == 0){
            throw new RuntimeException("当前项目不是一个spring boot项目!");
        }
        Object bean = applicationContext.getBean(beanNamesForAnnotation[0]);
        SpringBootApplication application = AnnotationUtil.getAnnotation(bean.getClass(), SpringBootApplication.class);

        String[] packages = application.scanBasePackages();
        if (packages == null || packages.length == 0){
            packages = new String[1];
            packages[0] = bean.getClass().getPackage().getName();
        }
        ClassPathHttpProxyScanner scanner = new ClassPathHttpProxyScanner(beanDefinitionRegistry);
        scanner.setResourceLoader(this.applicationContext);
        scanner.setAnnotationClass(this.annotationClass);
        scanner.setConfiguration(configuration);
        scanner.registerFilters();
        scanner.scan(packages);
    }

    /**
     * @author Marsh
     * @date 2021-12-16
     * @param configurableListableBeanFactory
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        /*String[] requestActuatorFactoryNames = configurableListableBeanFactory.getBeanNamesForType(RequestActuatorFactory.class);
        if (requestActuatorFactoryNames != null && requestActuatorFactoryNames.length > 0){
            for (String beanName : requestActuatorFactoryNames){
                RequestActuatorFactoryBean.addRequestActuatorFactory(configurableListableBeanFactory.getBean(beanName,RequestActuatorFactory.class));
            }
        }*/

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
