package com.wang.business.service.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.PostConstruct;


public  class TestSpringOrder implements ApplicationContextAware, BeanFactoryAware,
        InitializingBean,
        SmartLifecycle,
        BeanNameAware,
        ApplicationListener<ContextRefreshedEvent>,
        CommandLineRunner,
        SmartInitializingSingleton {
    private static final Logger log = LoggerFactory.getLogger(TestSpringOrder.class);


    public TestSpringOrder(){
        log.warn("SpringOrder 构造TestSpringOrder");
    }
    @Override
    public void start() {
        log.warn("SpringOrder LifeCycle...");
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.warn("SpringOrder BeanFactoryAware...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.warn("SpringOrder InitializingBean");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.warn("SpringOrder ApplicationContextAware...");
    }

    @Override
    public void setBeanName(String s) {
        log.warn("SpringOrder BeanFactoryAware...");
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.warn("SpringOrder SmartInitializingSingleton...");

    }

    @Override
    public void run(String... args) throws Exception {
        log.warn("SpringOrder CommandLineRunner...");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.warn("SpringOrder ApplicationListener...");
    }

    @PostConstruct
    public void postConstruct(){
        log.warn("SpringOrder PostConstruct...");
    }

    public void initMethod() {
        log.warn("启动顺序:init-method");
    }
}
