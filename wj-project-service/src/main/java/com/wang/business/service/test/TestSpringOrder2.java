package com.wang.business.service.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class TestSpringOrder2 implements
        BeanPostProcessor,
        BeanFactoryPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(TestSpringOrder.class);
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        log.warn("SpringOrder2 postProcessBeanFactory");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.warn("启动顺序:BeanPostProcessor postProcessBeforeInitialization beanName:{}", beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.warn("启动顺序:BeanPostProcessor postProcessAfterInitialization beanName:{}", beanName);
        return bean;
    }
}
